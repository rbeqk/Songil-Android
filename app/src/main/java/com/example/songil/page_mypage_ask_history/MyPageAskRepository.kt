package com.example.songil.page_mypage_ask_history

import com.example.songil.config.GlobalApplication
import com.example.songil.page_mypage_ask_history.models.ResponseGetMyPageAskList
import retrofit2.Response

class MyPageAskRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(MyPageAskRetrofitInterface::class.java)

    suspend fun getMyAskList(page : Int) : Response<ResponseGetMyPageAskList> {
        val result = retrofit.getMyPageAskList(page)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}