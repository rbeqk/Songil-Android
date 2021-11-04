package com.example.songil.page_signup

import com.example.songil.config.BaseResponse
import com.example.songil.data.PhoneNumber
import com.example.songil.page_signup.models.RequestSignUp
import com.example.songil.page_signup.models.ResponseAuthPhone
import com.example.songil.page_signup.models.ResponseSignUp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignupRetrofitInterface {
    @POST("auth/phone")
    suspend fun postAuthPhone(@Body params : PhoneNumber) : Response<ResponseAuthPhone>

    @GET("auth/duplicated-phone")
    suspend fun getPhoneNumberDuplicateCheck(@Query("phoneNumber") phoneNumber : String) : Response<BaseResponse>

    @GET("auth/duplicated-nickName")
    suspend fun getNickNameDuplicateCheck(@Query("nickName") nickName : String) : Response<BaseResponse>

    @POST("users")
    suspend fun postSignUp(@Body params : RequestSignUp) : Response<ResponseSignUp>
}