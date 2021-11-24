package com.example.songil.page_signup

import com.example.songil.config.BaseResponse
import com.example.songil.data.PhoneNumber
import com.example.songil.page_signup.models.*
import retrofit2.Response
import retrofit2.http.*

interface SignupRetrofitInterface {
    @GET("agreements")
    suspend fun getAgreements() : Response<ResponseAgreements>

    @GET("agreements/{agreementIdx}")
    suspend fun getAgreementDetail(@Path("agreementIdx")agreementIdx :Int) : Response<ResponseAgreementDetail>

    @POST("auth")   // 인증번호 발급
    suspend fun postAuth(@Body params : RequestAuth) : Response<BaseResponse>

    @GET("auth")    // 인증번호 확인
    suspend fun getAuth(@Query("phone") phone : String, @Query("verificationCode") verificationCode : String) : Response<BaseResponse>

    @POST("signup")
    suspend fun postSignUp(@Body params : RequestSignUp) : Response<ResponseSignUp>

    @GET("auth/duplicated-check")
    suspend fun getDuplicateCheck(@Query("phone") phone: String? = null, @Query("nickname") nickname : String? = null) : Response<BaseResponse>
}