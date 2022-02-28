package com.example.songil.page_notice

import com.example.songil.page_notice.models.ResponseGetNotice
import retrofit2.Response
import retrofit2.http.GET

interface NoticeRetrofitInterface {
    @GET("notice")
    suspend fun getNotice() : Response<ResponseGetNotice>
}