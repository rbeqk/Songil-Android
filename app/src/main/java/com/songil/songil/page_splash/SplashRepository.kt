package com.songil.songil.page_splash

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_splash.models.ResponseGetUserTypeBody

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