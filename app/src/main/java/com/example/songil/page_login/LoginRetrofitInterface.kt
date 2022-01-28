package com.example.songil.page_login

import com.example.songil.page_login.models.LoginInfo
import com.example.songil.page_login.models.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("login")
    suspend fun postLogin(@Body params : LoginInfo) : Response<ResponseLogin>
}