package com.example.songil.page_login

import com.example.songil.config.GlobalApplication
import com.example.songil.page_login.models.LoginInfo
import com.example.songil.page_login.models.ResponseLogin
import retrofit2.Response

class LoginRepository {
    private val loginRetrofitInterface = GlobalApplication.sRetrofit.create(LoginRetrofitInterface::class.java)

    suspend fun doLogin(loginInfo: LoginInfo) : Response<ResponseLogin>{
        val result = loginRetrofitInterface.postLogin(loginInfo)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}