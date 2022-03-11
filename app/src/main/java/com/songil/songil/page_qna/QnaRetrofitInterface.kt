package com.songil.songil.page_qna

import com.songil.songil.config.BaseResponse
import com.songil.songil.page_qna.models.RequestWriteComment
import com.songil.songil.page_qna.models.ResponseQna
import com.songil.songil.page_qna.models.ResponseQnaChat
import com.songil.songil.page_qna.models.ResponseQnaLike
import retrofit2.Response
import retrofit2.http.*

interface QnaRetrofitInterface {
    @GET("with/qna/{qnaIdx}/comments")
    suspend fun getComments(@Path("qnaIdx") qnaIdx : Int, @Query("page") page : Int) : Response<ResponseQnaChat>

    @GET("with/qna/{qnaIdx}")
    suspend fun getQna(@Path("qnaIdx") qnaIdx: Int) : Response<ResponseQna>

    @DELETE("with/qna/{qnaIdx}")
    suspend fun deleteQna(@Path("qnaIdx") qnaIdx : Int) : Response<BaseResponse>

    @POST("with/qna/{qnaIdx}/comments")
    suspend fun postQnaChat(@Path("qnaIdx") qnaIdx : Int, @Body params : RequestWriteComment) : Response<BaseResponse>

    @DELETE("with/qna/comments/{commentIdx}")
    suspend fun deleteQnaChat(@Path("commentIdx") commentIdx : Int) : Response<BaseResponse>

    @PATCH("with/qna/{qnaIdx}/like")
    suspend fun patchQnaLike(@Path("qnaIdx") qnaIdx : Int) : Response<ResponseQnaLike>
}