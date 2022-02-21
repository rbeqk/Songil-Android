package com.example.songil.page_splash

import com.example.songil.config.GlobalApplication
import com.example.songil.page_splash.models.ResponseGetUserTypeBody

class SplashRepository {
    private val splashRetrofitInterface = GlobalApplication.sRetrofit.create(SplashRetrofitInterface::class.java)

    suspend fun getAutoLogin() = splashRetrofitInterface.postAutoLogin()

    suspend fun getUserType() : ResponseGetUserTypeBody {
        val result = splashRetrofitInterface.getUserType()
        if (result.isSuccessful){
            return result.body()!!
        }
        throw UnknownError()
    }
}