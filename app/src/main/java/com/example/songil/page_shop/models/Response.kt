package com.example.songil.page_shop.models

import com.example.songil.config.BaseResponse

data class ResponseTodayArtists(val result : TodayArtistsResult) : BaseResponse()

data class ResponseNewCrafts(val result : ArrayList<NewCraft>) : BaseResponse()

data class ResponseTodayCraft(val result : ArrayList<TodayCraft>) : BaseResponse()