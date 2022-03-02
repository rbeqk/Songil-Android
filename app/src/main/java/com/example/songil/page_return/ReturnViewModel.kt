package com.example.songil.page_return

import androidx.lifecycle.*
import com.example.songil.config.BaseViewModel
import kotlinx.coroutines.launch

class ReturnViewModel(private val orderDetailIdx : Int) : BaseViewModel() {
    private val repository = ReturnRepository()

    var reasonIdx = MutableLiveData<Int>()
    var returnButtonActivate = MutableLiveData(false)
    var reasonContent = ""

    private val _sendReturnRequestResult = MutableLiveData<Int>()
    val sendReturnRequestResult : LiveData<Int> get() = _sendReturnRequestResult

    class ReturnViewModelFactory(private val orderDetailIdx :Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ReturnViewModel(orderDetailIdx) as T
        }
    }

    fun changeIdx(idx : Int) {
        reasonIdx.value = idx
        checkBtnActivate()
    }

    fun checkBtnActivate() {
        returnButtonActivate.value = ((reasonIdx.value!! in 0 until 3) || (reasonIdx.value!! == 3 && reasonContent.length in 1 until 151))
    }

    fun trySendReturnRequest(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.postReturnRequest(orderDetailIdx = orderDetailIdx, reasonIdx = reasonIdx.value!! + 1, if (reasonContent == "" || reasonIdx.value!! != 3) null else reasonContent)
            _sendReturnRequestResult.postValue(result.code)
        }
    }

    fun deactivateButton(){
        returnButtonActivate.value = false
    }
}