package com.example.songil.data

data class Order(val orderIdx : Int, val productName : String, val productThumbnail : String, val artistName : String,
                 val price : Int, val orderStatus : String, val amount : Int, val productIdx : Int)

data class OrderForm(val craft : ArrayList<Craft4>, var recipient : String = "", var phoneNumber : String = "",
                     var zipCode : String = "", var address : String ="", var detailAddress : String ="", val havePoint : Int = 0,
                     var usePoint : Int = 0, var useCouponIdx : Int ?= null)

data class Orders(val date : String, val orderList : ArrayList<Order>)
