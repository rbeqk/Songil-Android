package com.example.songil.page_qna

import com.example.songil.page_qna.models.ResponseQna
import com.example.songil.page_qna.models.ResponseQnaChat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QnaRetrofitInterface {
    @GET("with/qna/{qnaIdx}/comments")
    suspend fun getComments(@Path("qnaIdx") qnaIdx : Int, @Query("page") page : Int) : Response<ResponseQnaChat>

    @GET("with/qna/{qnaIdx}")
    suspend fun getQna(@Path("qnaIdx") qnaIdx: Int) : Response<ResponseQna>
}