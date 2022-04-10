package com.songil.songil.page_with

import com.songil.songil.config.GlobalApplication

class WithRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(WithRetrofitInterface::class.java)

    suspend fun getHotTalk() = retrofitInterface.getHotTalk()

    suspend fun postBlockUser(userIdx : Int) : Int {
        val result = retrofitInterface.postBlockUser(userIdx)
        if (result.isSuccessful){
            return result.body()?.code ?: -1
        }
        throw UnknownError()
    }
}