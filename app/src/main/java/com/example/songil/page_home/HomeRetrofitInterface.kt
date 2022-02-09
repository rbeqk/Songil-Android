package com.example.songil.page_home

import com.example.songil.page_home.models.HomeData
import com.example.songil.page_home.models.ResponseGetHome
import retrofit2.Response
import retrofit2.http.GET

interface HomeRetrofitInterface {
    @GET("home")
    suspend fun getHomeData() : Response<ResponseGetHome>
}