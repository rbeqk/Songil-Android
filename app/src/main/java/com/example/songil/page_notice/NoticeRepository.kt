package com.example.songil.page_notice

import com.example.songil.config.GlobalApplication
import com.example.songil.page_notice.models.ResponseGetNotice

class NoticeRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(NoticeRetrofitInterface::class.java)

    suspend fun getNotice() : ResponseGetNotice {
        val result = retrofit.getNotice()
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}