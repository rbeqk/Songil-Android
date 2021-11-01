package com.example.songil.page_signup

import com.example.songil.config.BaseResponse
import com.example.songil.data.PhoneNumber
import com.example.songil.page_signup.models.ResponseAuthPhone
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignupRetrofitInterface {
    @POST("auth/phone")
    fun postAuthPhone(@Body params : PhoneNumber) : Call<ResponseAuthPhone>

    @GET("/auth/duplicated-phone")
    fun getPhoneNumberDuplicateCheck(@Query("phoneNumber") phoneNumber : String) : Call<BaseResponse>

    @GET("/auth/duplicated-nickName?nickName=")
    fun getNickNameDuplicateCheck(@Query("nickName") nickName : String) : Call<BaseResponse>
}