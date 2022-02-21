package com.example.songil.page_splash

import com.example.songil.page_splash.models.ResponseAutoLoginBody
import com.example.songil.page_splash.models.ResponseGetUserTypeBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface SplashRetrofitInterface {
    @POST("login/auto")
    suspend fun postAutoLogin() : Response<ResponseAutoLoginBody>

    @GET("users/type")
    suspend fun getUserType() : Response<ResponseGetUserTypeBody>
}