package com.example.songil.page_login

import com.example.songil.config.GlobalApplication
import com.example.songil.data.PhoneNumber
import com.example.songil.page_login.models.RequestLogin

class LoginRepository {
    private val loginRetrofitInterface = GlobalApplication.sRetrofit.create(LoginRetrofitInterface::class.java)

    suspend fun setAuthNumber(phoneNumber: String) = loginRetrofitInterface.postAuthLogin(PhoneNumber(phoneNumber))

    suspend fun tryLogin(phoneNumber: String, verificationCode : String) = loginRetrofitInterface.postLogin(RequestLogin(phoneNumber, verificationCode))
}