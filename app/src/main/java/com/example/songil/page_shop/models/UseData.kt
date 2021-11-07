package com.example.songil.page_shop.models

data class TodayArtistsResult(val name : String, val profileImgUrl : String, val major : String)

data class NewCraft(val productIdx : Int, val thumbNailImg : String)

data class TodayCraft(val productIdx : Int, val productName : String, val price : Int, val artistName : String, val thumbNailImg: String)