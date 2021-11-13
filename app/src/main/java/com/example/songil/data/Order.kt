package com.example.songil.data

data class Order(val orderIdx : Int, val productName : String, val productThumbnail : String, val artistName : String,
                 val price : Int, val orderStatus : String, val amount : Int, val productIdx : Int)
