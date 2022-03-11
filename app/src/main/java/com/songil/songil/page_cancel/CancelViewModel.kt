package com.songil.songil.page_cancel

import androidx.lifecycle.*
import com.songil.songil.config.BaseViewModel
import kotlinx.coroutines.launch

class CancelViewModel(private val orderDetailIdx: Int) : BaseViewModel() {

    private val repository = CancelRepository()

    var reasonIdx = MutableLiveData<Int>()
    var cancelButtonActivate = MutableLiveData(false)
    var reasonContent = ""

    private val _sendCancelRequestResult = MutableLiveData<Int>()
    val sendCancelRequestResult : LiveData<Int> get() = _sendCancelRequestResult

    class CancelViewModelFactory(private val orderDetailIdx : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CancelViewModel(orderDetailIdx) as T
        }
    }

    fun changeIdx(idx : Int){
        reasonIdx.value = idx
        checkBtnActivate()
    }

    fun checkBtnActivate(){
        cancelButtonActivate.value = ((reasonIdx.value!! in 0 until 4) || reasonContent.length in 1 until 151)
    }

    fun trySendCancelRequest(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.requestOrderCancel(orderDetailIdx = orderDetailIdx, reasonIdx = reasonIdx.value!! + 1, if(reasonContent == "" || reasonIdx.value!! != 4) null else reasonContent)
            _sendCancelRequestResult.postValue(result.code)
        }
    }

    fun deactivateButton(){
        cancelButtonActivate.value = false
    }
}