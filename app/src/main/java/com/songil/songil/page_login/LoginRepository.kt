package com.songil.songil.page_login

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_login.models.LoginInfo
import com.songil.songil.page_login.models.ResponseGetUserTypeBody
import com.songil.songil.page_login.models.ResponseLogin
import retrofit2.Response

class LoginRepository {
    private val loginRetrofitInterface = GlobalApplication.sRetrofit.create(LoginRetrofitInterface::class.java)

    suspend fun doLogin(loginInfo: LoginInfo) : Response<ResponseLogin>{
        val result = loginRetrofitInterface.postLogin(loginInfo)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun getUserType() : ResponseGetUserTypeBody {
        val result = loginRetrofitInterface.getUserType()
        if (result.isSuccessful){
            return result.body()!!
        }
        throw UnknownError()
    }
}