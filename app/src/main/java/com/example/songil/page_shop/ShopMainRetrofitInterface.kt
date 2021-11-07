package com.example.songil.page_shop

import com.example.songil.page_shop.models.ResponseNewCrafts
import com.example.songil.page_shop.models.ResponseTodayArtists
import com.example.songil.page_shop.models.ResponseTodayCraft
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopMainRetrofitInterface {
    @GET("artists/today")
    suspend fun getTodayArtists() : Response<ResponseTodayArtists>

    @GET("crafts")
    suspend fun getTodayCrafts(@Query("state") state : String) : Response<ResponseTodayCraft>

    @GET("crafts")
    suspend fun getNewCrafts(@Query("state") state: String) : Response<ResponseNewCrafts>
}