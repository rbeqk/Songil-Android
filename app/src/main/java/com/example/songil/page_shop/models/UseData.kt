package com.example.songil.page_shop.models

data class TodayArtistsResult(val name : String, val profileImgUrl : String, val major : String)

data class NewCraft(val productIdx : Int, val thumbNailImg : String)

data class TodayCraft(val productIdx : Int, val name : String, val price : Int, val artist : String, val imageUrl: String)