package com.example.songil.page_login

import com.example.songil.data.PhoneNumber
import com.example.songil.page_login.models.ResponseAuthPhone
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
//import io.reactivex.Observable

interface LoginRetrofitInterface {
    @POST("auth/phone")
    fun postAuthPhone(@Body params : PhoneNumber) : Call<ResponseAuthPhone>

    @POST("auth/login")
    fun postAuthLogin()
}