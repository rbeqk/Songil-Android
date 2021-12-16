package com.example.songil.page_shop.shop_category.models

data class CraftSimple(val productIdx : Int, val productName : String, val thumbnailImg : String, val price : Int, val artistName : String)

data class CraftDetail(val productIdx : Int, val productName : String, val thumbnailImg : String, var likeCount : Int, var reviewCount : Int,
                       val price : Int, val artistName : String, val newOrNot : String, var likeOrNot : Int)

data class ProductAllResult(val popularList : ArrayList<CraftSimple>, val productsList : ArrayList<CraftDetail>)

data class LikeData(val isLike : Boolean, val totalLikeCnt : Int, val itemPosition : Int) // itemPosition is item position in recyclerView Adapter