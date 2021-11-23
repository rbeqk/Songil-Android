package com.example.songil.data

data class CraftSimpleInfo(val craftName : String, val maker : String, val price : Int, val thumbnail : String)

data class ProductDetailInfo(val productIdx : Int, val isNew : String, val mainImgUrl : String, val name : String,
                             val price : Int, val shippingFee : ArrayList<String>, val material : ArrayList<String>,
                             val usage : ArrayList<String>, val content : String, val size : String, val cautions : ArrayList<String>,
                             val detailImageUrls : ArrayList<String>, val artistIdx : Int, val artistName : String, val artistIntroduction : String, val totalReviewCnt : Int,
                             val artistImageUrl : String? = null)

