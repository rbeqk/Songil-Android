package com.songil.songil.page_signup

import com.songil.songil.config.BaseResponse
import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_signup.models.RequestAuth
import com.songil.songil.page_signup.models.RequestSignUp
import com.songil.songil.page_signup.models.ResponseSignUp
import retrofit2.Response

class SignupRepository private constructor() {
    companion object {
        @Volatile private var INSTANCE : SignupRepository ?= null

        private val signupRetrofitInterface = GlobalApplication.sRetrofit.create(SignupRetrofitInterface::class.java)

        fun getInstance() : SignupRepository =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: SignupRepository().also { INSTANCE = it }
            }

        fun removeInstance() {
            INSTANCE ?: synchronized(this){
                INSTANCE = null
            }
        }
    }

    suspend fun issueAuthCode(email : String) : Response<BaseResponse>{
        val response = signupRetrofitInterface.postAuth(RequestAuth(email))
        if (response.isSuccessful) return response
        else throw UnknownError()
    }

    suspend fun checkAuthCode(email : String, authCode : String) : Response<BaseResponse> {
        val response = signupRetrofitInterface.getAuthCheck(email, authCode)
        if (response.isSuccessful) return response
        else throw UnknownError()
    }

    suspend fun checkNickname(nickname : String) : Response<BaseResponse> {
        val response = signupRetrofitInterface.getNicknameDuplicateCheck(nickname)
        if (response.isSuccessful) return response
        else throw UnknownError()
    }

    suspend fun signup(signupInfo : RequestSignUp) : Response<ResponseSignUp> {
        val response = signupRetrofitInterface.postSignUp(signupInfo)
        if (response.isSuccessful) return response
        else throw UnknownError()
    }
}