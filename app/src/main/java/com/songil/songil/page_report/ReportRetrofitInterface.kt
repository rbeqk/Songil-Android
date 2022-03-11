package com.songil.songil.page_report

import com.songil.songil.config.BaseResponse
import com.songil.songil.page_report.models.ReportData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportRetrofitInterface {
    @POST("with/stories/comments/{commentIdx}/reported")
    suspend fun postStoryCommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("with/stories/{storyIdx}/reported")
    suspend fun postStoryReport(@Path("storyIdx") storyIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("with/qna/comments/{commentIdx}/reported")
    suspend fun postQnACommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("with/qna/{qnaIdx}/reported")
    suspend fun postQnAReport(@Path("qnaIdx") qnaIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("with/ab-test/comments/{commentIdx}/reported")
    suspend fun postAbtestCommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("with/ab-test/{abTestIdx}/reported")
    suspend fun postAbtestReport(@Path("abTestIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("")
    suspend fun postArticleReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("")
    suspend fun postArticleCommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("shop/crafts/comments/{commentIdx}/reported")
    suspend fun postCraftCommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>
}