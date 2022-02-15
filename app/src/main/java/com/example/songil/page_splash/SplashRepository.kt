package com.example.songil.page_splash

import com.example.songil.config.GlobalApplication

class SplashRepository {
    private val splashRetrofitInterface = GlobalApplication.sRetrofit.create(SplashRetrofitInterface::class.java)

    suspend fun getAutoLogin() = splashRetrofitInterface.postAutoLogin()
}