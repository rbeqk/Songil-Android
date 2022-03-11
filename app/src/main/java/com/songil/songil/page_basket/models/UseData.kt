package com.songil.songil.page_basket.models

data class CartItem(val craftIdx : Int, val mainImageUrl : String, val name : String, val artistIdx : Int, val artistName : String, val price : Int, var amount : Int, var checked : Boolean ?= null)

data class Amount(val amount : Int)

// after amount change api called, used in Adapter to notifyItemChange(position)
data class AmountAndPosition(val amount : Int, val position : Int)