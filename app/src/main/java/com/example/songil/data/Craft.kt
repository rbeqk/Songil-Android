package com.example.songil.data

data class CraftSimpleInfo(val craftName : String, val artist : String, val price : Int, val imageUrl : String, val craftIdx : Int = 0)

data class CraftDetailInfo(val craftIdx : Int, val isNew : String, val isSoldOut : String, val mainImageUrl : String, val name : String,
                           val price : Int, val shippingFee : ArrayList<String>, val material : ArrayList<String>,
                           val usage : ArrayList<String>, val content : String, val size : String, val cautions : ArrayList<String>,
                           val detailImageUrls : ArrayList<String>, val artistIdx : Int, val artistName : String, val artistIntroduction : String, val totalCommentCnt : Int,
                           val artistImageUrl : String? = null)

data class Craft2(val productIdx : Int, val imageUrl : String, val name : String, val artist : String, val price : Int, val isNew : String)

data class Craft4(val productIdx : Int, val imageUrl : String, val name : String, val artist : String, val price : Int, val count : Int)

data class CraftComment(val commentIdx : Int, val userIdx : Int, val nickname : String, val createdAt : String, val imageUrl : ArrayList<String>?, val content : String, val isReported : String)

data class Craft1(val craftIdx : Int, val mainImageUrl : String, val name : String, val artistIdx : Int, val artistName : String,
                  val price : Int, val isNew : String, val isSoldOut : String, var totalLikeCnt : Int, var isLike : String, var totalCommentCnt : Int)