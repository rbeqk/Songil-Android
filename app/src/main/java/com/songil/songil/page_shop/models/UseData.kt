package com.songil.songil.page_shop.models

import com.songil.songil.data.Craft2

data class TodayArtistsResult(val artistIdx : Int, val artistName : String, val imageUrl : String, val major : String)

data class NewCraft(val craftIdx : Int, val mainImageUrl : String)

data class ShopMainBanner(val bannerIdx : Int, val imageUrl : String)

data class ShopMainData(val banner : ArrayList<ShopMainBanner>, val todayCraft : ArrayList<Craft2>, val todayArtist : TodayArtistsResult, val newCraft : ArrayList<NewCraft>)