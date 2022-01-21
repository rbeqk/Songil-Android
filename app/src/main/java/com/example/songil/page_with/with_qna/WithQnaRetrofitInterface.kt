package com.example.songil.page_with.with_qna

import com.example.songil.page_with.with_qna.models.ResponseGetQna
import com.example.songil.page_with.with_qna.models.ResponseQnaPageCnt
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WithQnaRetrofitInterface {
    @GET("with")
    suspend fun getQna(@Query("category") category : String, @Query("sort") sort : String, @Query("page") page : Int) : Response<ResponseGetQna>

    @GET("with/page")
    suspend fun getQnaPageCnt(@Query("category") category : String = "qna") : Response<ResponseQnaPageCnt>
}