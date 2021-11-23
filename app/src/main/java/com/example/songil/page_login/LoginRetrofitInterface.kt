package com.example.songil.page_login

import com.example.songil.config.BaseResponse
import com.example.songil.data.PhoneNumber
import com.example.songil.page_login.models.RequestLogin
import com.example.songil.page_login.models.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("auth/login")
    suspend fun postAuthLogin(@Body params: PhoneNumber) : Response<BaseResponse>

    @POST("login")
    suspend fun postLogin(@Body params : RequestLogin) : Response<ResponseLogin>

}