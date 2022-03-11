package com.songil.songil.page_craft.models

data class CraftBaseInfo(val NewOrNot : String, val productName : String, val price : Int, val shippingFee : String,
                         val productSubject : String, val purpose : String, val artistIdx : Int, val artistName : String,
                         val artistProfileImg : String, val artistIntro : String, val thumbNailImg : String, val likeOrNot : Int,
                         val reviewCount : Int, val askCount : Int)

data class CraftDetailInfo(val productIntro : String, val width : Int, val depth : Int, val height : Int, val caution : String, val imgs : ArrayList<String>)

data class CraftReview(val commentIdx : Int, val content : String, val createdAt : String, val nickname : String, val imageUrl : ArrayList<String>)

data class ProductDetailResult(val infoResult : CraftBaseInfo, val detailResult : CraftDetailInfo, val reviewResults : ArrayList<CraftReview>)