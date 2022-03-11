package com.songil.songil.page_mypage_ask_history

import com.songil.songil.page_mypage_ask_history.models.ResponseGetMyPageAskList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPageAskRetrofitInterface {
    @GET("my-page/ask")
    suspend fun getMyPageAskList(@Query("page") page : Int) : Response<ResponseGetMyPageAskList>
}