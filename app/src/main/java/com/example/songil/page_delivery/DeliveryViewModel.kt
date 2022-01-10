package com.example.songil.page_delivery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songil.data.DeliveryStatus
import kotlinx.coroutines.launch

class DeliveryViewModel : ViewModel() {

    private val repository = DeliveryRepository()
    lateinit var dataList : ArrayList<DeliveryStatus>
    var deliveryResult = MutableLiveData<Int>()

    fun tempGetDelivery(){
        viewModelScope.launch {
            dataList = repository.getDelivery()
            deliveryResult.postValue(200)
        }
    }
}