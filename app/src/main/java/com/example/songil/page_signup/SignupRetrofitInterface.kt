package com.example.songil.page_signup

import com.example.songil.config.BaseResponse
import com.example.songil.page_signup.models.*
import retrofit2.Response
import retrofit2.http.*

interface SignupRetrofitInterface {

    @POST("auth")   // 인증번호 발급
    suspend fun postAuth(@Body params : RequestAuth) : Response<BaseResponse>

    @GET("auth")    // 인증번호 확인
    suspend fun getAuthCheck(@Query("email") email : String, @Query("code") code : String) : Response<BaseResponse>

    @POST("signup")
    suspend fun postSignUp(@Body params : RequestSignUp) : Response<ResponseSignUp>

    @GET("auth/duplicated-check")
    suspend fun getNicknameDuplicateCheck(@Query("nickname") nickname : String) : Response<BaseResponse>
}