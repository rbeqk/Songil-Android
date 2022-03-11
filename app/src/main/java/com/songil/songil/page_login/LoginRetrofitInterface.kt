package com.songil.songil.page_login

import com.songil.songil.page_login.models.LoginInfo
import com.songil.songil.page_login.models.ResponseLogin
import com.songil.songil.page_login.models.ResponseGetUserTypeBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("login")
    suspend fun postLogin(@Body params : LoginInfo) : Response<ResponseLogin>

    @GET("users/type")
    suspend fun getUserType() : Response<ResponseGetUserTypeBody>
}