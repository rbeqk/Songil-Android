package com.example.songil.page_delivery

import androidx.lifecycle.*
import com.example.songil.config.BaseViewModel
import com.example.songil.data.DeliveryStatus
import kotlinx.coroutines.launch

class DeliveryViewModel(private val detailOrderIdx: Int) : BaseViewModel() {

    class DeliveryViewModelFactory(private val detailOrderIdx : Int) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DeliveryViewModel(detailOrderIdx) as T
        }

    }

    private val repository = DeliveryRepository()
    val dataList = arrayListOf<DeliveryStatus>()

    var tCode = ""
    var tInvoice = ""

    private val _deliveryTrackingResult = MutableLiveData<Int>()
    val deliveryTrackingResult : LiveData<Int> get() = _deliveryTrackingResult

    fun tryGetTrackingData(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getTrackingData(detailOrderIdx)
            when (result.code){
                200 -> {
                    tCode = result.result.tCode
                    tInvoice = result.result.tInvoice
                    dataList.clear()
                    dataList.addAll(result.result.trackingInfo)
                }
            }
            _deliveryTrackingResult.postValue(result.code)
        }
    }
}