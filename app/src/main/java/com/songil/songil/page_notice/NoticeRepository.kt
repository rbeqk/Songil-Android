package com.songil.songil.page_notice

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_notice.models.ResponseGetNotice

class NoticeRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(NoticeRetrofitInterface::class.java)

    suspend fun getNotice() : ResponseGetNotice {
        val result = retrofit.getNotice()
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}