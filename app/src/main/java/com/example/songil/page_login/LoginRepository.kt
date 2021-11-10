package com.example.songil.page_login

import com.example.songil.config.GlobalApplication
import com.example.songil.data.PhoneNumber

class LoginRepository {
    private val loginRetrofitInterface = GlobalApplication.sRetrofit.create(LoginRetrofitInterface::class.java)

    suspend fun getAuthNumber(phoneNumber : String) = loginRetrofitInterface.postAuthPhone(PhoneNumber(phoneNumber))

    suspend fun getLogin(phoneNumber: String) = loginRetrofitInterface.postLogin(PhoneNumber(phoneNumber))

    suspend fun getUserIdx() = loginRetrofitInterface.getAuthJwt()
}