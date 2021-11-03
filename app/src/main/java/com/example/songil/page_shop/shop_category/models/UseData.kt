package com.example.songil.page_shop.shop_category.models

data class CraftSimple(val productIdx : Int, val productName : String, val thumbnailImg : String, val price : Int, val artistName : String)

data class CraftDetail(val productIdx : Int, val productName : String, val thumbnailImg : String, val likeCount : Int, val reviewCount : Int,
                       val price : Int, val artistName : String, val newOrNot : String, val likeOrNot : Int)

data class ProductAllResult(val popularList : ArrayList<CraftSimple>, val productsList : ArrayList<CraftDetail>)