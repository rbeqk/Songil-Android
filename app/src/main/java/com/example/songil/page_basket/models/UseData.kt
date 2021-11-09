package com.example.songil.page_basket.models

data class BasketItem(val cartIdx : Int, val productIdx : Int, val productName : String, val artistName : String, var amount : Int, val price : Int, val thumbNailImg : String, var checked: Boolean = true)