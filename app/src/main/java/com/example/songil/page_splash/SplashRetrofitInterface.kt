package com.example.songil.page_splash

import com.example.songil.page_splash.models.RequestAuthLogin
import com.example.songil.page_splash.models.ResponseAuthJwt
import com.example.songil.page_splash.models.ResponseAuthLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SplashRetrofitInterface {
    @POST("auth/login")
    suspend fun postAuthLogin(@Body params : RequestAuthLogin) : Response<ResponseAuthLogin>

    @GET("auth/jwt")
    suspend fun getAuthJwt() : Response<ResponseAuthJwt>
}