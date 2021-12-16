package com.example.songil.data

data class ProductSimpleInfo(val craftName : String, val maker : String, val price : Int, val thumbnail : String)

data class ProductDetailInfo(val productIdx : Int, val isNew : String, val mainImageUrl : String, val name : String,
                             val price : Int, val shippingFee : ArrayList<String>, val material : ArrayList<String>,
                             val usage : ArrayList<String>, val content : String, val size : String, val cautions : ArrayList<String>,
                             val detailImageUrls : ArrayList<String>, val artistIdx : Int, val artistName : String, val artistIntroduction : String, val totalReviewCnt : Int,
                             val artistImageUrl : String? = null)

data class ProductReview(val reviewIdx : Int, val userIdx : Int, val nickname : String, val createAt : String, val imageUrl : ArrayList<String> ?= null, val content : String, val isReported : String)

data class Craft2(val productIdx : Int, val imageUrl : String, val name : String, val artist : String, val price : Int, val isNew : String)