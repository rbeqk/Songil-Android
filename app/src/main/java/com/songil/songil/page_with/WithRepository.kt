package com.songil.songil.page_with

import com.songil.songil.config.GlobalApplication

class WithRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(WithRetrofitInterface::class.java)

    suspend fun getHotTalk() = retrofitInterface.getHotTalk()
}