package com.example.songil.page_login

import com.example.songil.data.PhoneNumber
import com.example.songil.page_login.models.ResponseAuthPhone
import com.example.songil.page_login.models.ResponseLogin
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("auth/phone")
    suspend fun postAuthPhone(@Body params : PhoneNumber) : Response<ResponseAuthPhone>

    @POST("auth/login")
    suspend fun postLogin(@Body params : PhoneNumber) : Response<ResponseLogin>
}