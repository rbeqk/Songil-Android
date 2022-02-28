package com.example.songil.page_customer_center

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.data.Notice
import kotlinx.coroutines.launch

class CustomerCenterViewModel : BaseViewModel() {
    private val repository = CustomerCenterRepository()

    private val _getFnqResult = MutableLiveData<Int>()
    val getFnqResult : LiveData<Int> get() = _getFnqResult

    var fnqList = arrayListOf<Notice>()

    fun tryGetFnq(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getFnq()
            if (result.code == 200){
                fnqList = result.result
            }
            _getFnqResult.postValue(result.code)
        }
    }
}