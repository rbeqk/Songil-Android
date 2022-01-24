package com.example.songil.page_qnawrite

import com.example.songil.config.BaseResponse
import com.example.songil.page_qnawrite.models.BodyQnaWrite
import com.example.songil.page_qnawrite.models.ResponseWriteQna
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface QnaWriteRetrofitInterface {
    @POST("with/qna")
    suspend fun postQna(@Body params : BodyQnaWrite) : Response<ResponseWriteQna>

    @PATCH("with/qna/{qnaIdx}")
    suspend fun patchQna(@Path("qnaIdx") qnaIdx : Int, @Body params : BodyQnaWrite) : Response<BaseResponse>
}