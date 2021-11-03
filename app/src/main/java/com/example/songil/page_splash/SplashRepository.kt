package com.example.songil.page_splash

import com.example.songil.config.GlobalApplication
import com.example.songil.page_splash.models.RequestAuthLogin

class SplashRepository {
    private val splashRetrofitInterface = GlobalApplication.sRetrofit.create(SplashRetrofitInterface::class.java)

    suspend fun postAuthLogin() = splashRetrofitInterface.postAuthLogin(RequestAuthLogin("11111111111"))

    suspend fun getAuthJwt() = splashRetrofitInterface.getAuthJwt()
}