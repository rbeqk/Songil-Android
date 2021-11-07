package com.example.songil.page_shop

import com.example.songil.config.GlobalApplication

class ShopMainRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ShopMainRetrofitInterface::class.java)

    suspend fun getTodayArtists() = retrofitInterface.getTodayArtists()

    suspend fun getTodayCrafts() = retrofitInterface.getTodayCrafts("today")

    suspend fun getNewCrafts() = retrofitInterface.getNewCrafts("new")
}