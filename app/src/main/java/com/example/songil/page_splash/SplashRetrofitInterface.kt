package com.example.songil.page_splash

import com.example.songil.config.BaseResponse
import retrofit2.Response
import retrofit2.http.POST

interface SplashRetrofitInterface {
    @POST("login/auto")
    suspend fun postAutoLogin() : Response<BaseResponse>
}