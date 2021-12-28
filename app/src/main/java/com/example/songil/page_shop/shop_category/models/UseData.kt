package com.example.songil.page_shop.shop_category.models

data class CraftSimple(val productIdx : Int, val name : String, val imageUrl : String, val price : Int, val artist : String)

data class CraftDetail(val craftIdx : Int, val name : String, val mainImageUrl : String, var totalLikeCnt : Int, var totalCommentCnt : Int,
                       val price : Int, val artistName : String, val isNew : String, var isLike : Int, val artistIdx : Int)

data class ProductAllResult(val popularList : ArrayList<CraftSimple>, val productsList : ArrayList<CraftDetail>)

data class LikeData(val isLike : Boolean, val totalLikeCnt : Int, val itemPosition : Int) // itemPosition is item position in recyclerView Adapter