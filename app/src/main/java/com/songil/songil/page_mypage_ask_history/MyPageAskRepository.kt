package com.songil.songil.page_mypage_ask_history

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_mypage_ask_history.models.ResponseGetMyPageAskList

class MyPageAskRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(MyPageAskRetrofitInterface::class.java)

    suspend fun getMyAskList(page : Int) : ResponseGetMyPageAskList {
        val result = retrofit.getMyPageAskList(page)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}