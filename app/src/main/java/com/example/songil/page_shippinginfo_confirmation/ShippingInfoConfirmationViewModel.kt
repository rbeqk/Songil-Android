package com.example.songil.page_shippinginfo_confirmation

import androidx.lifecycle.*
import com.example.songil.config.BaseViewModel
import com.example.songil.config.GlobalApplication
import kotlinx.coroutines.launch

class ShippingInfoConfirmationViewModel(private val orderDetailIdx : Int) : BaseViewModel() {
    class ShippingInfoConfirmationViewModelFactory(private val orderDetailIdx : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ShippingInfoConfirmationViewModel(orderDetailIdx) as T
        }
    }

    private val repository = ShippingInfoConfirmationRepository()

    // 발송정보 입력 데이터 조회의 result Code
    private val _getShippingInfoResult = MutableLiveData<Int>()
    val getShippingInfoResult : LiveData<Int> get() = _getShippingInfoResult

    var date = ""
    var courierCompany = ""
    var waybillNumber = ""

    fun tryGetShippingInfo(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getShippingInfo(orderDetailIdx)
            if (result.code == 200) {
                date = "${result.result.year}년 ${result.result.month}월 ${result.result.day}일"
                courierCompany = GlobalApplication.courierMap[result.result.tCode] ?: "조회할 수 없는 택배사"
                waybillNumber = result.result.tInvoice
            }
            _getShippingInfoResult.postValue(result.code)
        }
    }
}