package com.example.songil.page_shippinginfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.songil.config.BaseViewModel

class ShippingInfoViewModel(private val orderDetailIdx : Int) : BaseViewModel() {

    class ShippingInfoViewModelFactory(private val orderDetailIdx: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ShippingInfoViewModel(orderDetailIdx) as T
        }
    }

    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    fun setDate(inputYear : Int, inputMonth : Int, inputDay : Int) {
        year = inputYear
        month = inputMonth + 1
        day = inputDay
    }

    fun tempGetStatusString() : String {
        return "${year}.${month}.${day} : orderDetailIdx $orderDetailIdx"
    }

}