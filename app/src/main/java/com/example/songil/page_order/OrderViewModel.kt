package com.example.songil.page_order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.Craft4
import com.example.songil.data.OrderForm

class OrderViewModel : ViewModel() {
    var orderFormResult = MutableLiveData<Int>()
    var craftList = ArrayList<Craft4>()
    var recipient = ""
    var phoneNumber = ""
    var zipCode = ""
    var address = ""
    var detailAddress = ""
    var memo = ""
    var point = "0"
    var havePoint = 0
    var coupon = "없음"
    var couponIdx = -1
    var shippingFee = 0
    var totalPrice = 0

    fun tryGetOrderForm(){
        val orderForm = OrderForm(arrayListOf(Craft4(1, "https://cdn.class101.net/images/07064f5a-c599-4c8b-b77a-a2c0857849ef/original", "상품", "손길", 30000, 1)))
        recipient = orderForm.recipient
        zipCode = orderForm.zipCode
        address = orderForm.address
        detailAddress = orderForm.detailAddress
        craftList = orderForm.craft
        havePoint = orderForm.havePoint
        orderFormResult.value = 200
    }

    private fun getTotalPrice(){
        for (craft in craftList){
            totalPrice += craft.price
        }
        totalPrice += shippingFee
    }
}