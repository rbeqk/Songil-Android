package com.example.songil.page_mypage

import com.example.songil.data.PhoneNumber
import com.example.songil.page_mypage.models.ResponseAuthJwt
import com.example.songil.page_mypage.models.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MypageRetrofitInterface {
    @POST("auth/login")
    suspend fun postLogin(@Body params : PhoneNumber) : Response<ResponseLogin>

    @GET("auth/jwt")
    suspend fun getAuthJwt() : Response<ResponseAuthJwt>
}