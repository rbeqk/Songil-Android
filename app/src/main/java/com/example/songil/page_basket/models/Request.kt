package com.example.songil.page_basket.models

data class RequestFixCarts(val cartIdx : Int, val amount : Int)

data class RequestDeleteItem(val cartIdx : Int)