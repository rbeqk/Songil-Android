package com.example.songil.page_orderstatus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.Order
import com.example.songil.data.Orders

class OrderstatusViewModel : ViewModel() {
    var ordersData = MutableLiveData<ArrayList<Orders>>()

    fun getData(){
        val tempData = ArrayList<Orders>()
        tempData.add(Orders("2021.12.12", arrayListOf(Order(1, "상품1", "썸네일", "자까", 30000, "배송중", 1, 3))))
        tempData.add(Orders("2021.12.20", arrayListOf(Order(1, "상품2", "썸네일", "자까", 25000, "배송중", 1, 3), Order(1, "상품1", "썸네일", "자까", 30000, "배송중", 1, 3))))
        ordersData.value = tempData
    }
}