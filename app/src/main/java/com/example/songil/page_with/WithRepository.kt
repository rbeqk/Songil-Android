package com.example.songil.page_with

import com.example.songil.config.GlobalApplication

class WithRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(WithRetrofitInterface::class.java)

    suspend fun getHotTalk() = retrofitInterface.getHotTalk()
}