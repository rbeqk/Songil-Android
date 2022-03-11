package com.songil.songil.page_shippinginfo

import androidx.lifecycle.*
import com.songil.songil.config.BaseViewModel
import kotlinx.coroutines.launch

class ShippingInfoViewModel(private val orderDetailIdx : Int) : BaseViewModel() {

    class ShippingInfoViewModelFactory(private val orderDetailIdx: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ShippingInfoViewModel(orderDetailIdx) as T
        }
    }

    private val repository = ShippingInfoRepository()

    private val _sendingInfoResult = MutableLiveData<Int>()
    val sendingInfoResult : LiveData<Int> get() = _sendingInfoResult

    var btnActivate = MutableLiveData(false)

    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0
    var tCode = ""
    var waybillNumber : String = ""

    fun setDate(inputYear : Int, inputMonth : Int, inputDay : Int) {
        year = inputYear
        month = inputMonth + 1
        day = inputDay
    }

    fun checkBtnActivate(){
        btnActivate.value = (year != 0 && month != 0 && day != 0 && tCode != "" && waybillNumber != "")
    }

    fun deactivateBtn(){
        btnActivate.value = false
    }

    fun tryUploadSendingInfo(){
        viewModelScope.launch(exceptionHandler) {
            _sendingInfoResult.postValue(repository.postSendingInfo(orderDetailIdx, year, month, day, tCode, tInvoice = waybillNumber))
        }
    }

}