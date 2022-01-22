package com.example.songil.page_report

import com.example.songil.config.BaseResponse
import com.example.songil.page_report.models.ReportData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportRetrofitInterface {
    @POST("with/stories/comments/{commentIdx}/reported")
    suspend fun postStoryCommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("")
    suspend fun postStoryReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("with/qna/comments/{commentIdx}/reported")
    suspend fun postQnACommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("")
    suspend fun postQnAReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("with/ab-test/comments/{commentIdx}/reported")
    suspend fun postAbtestCommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("")
    suspend fun postAbtestReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("")
    suspend fun postArticleReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("")
    suspend fun postArticleCommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>

    @POST("")
    suspend fun postCraftCommentReport(@Path("commentIdx") commentIdx : Int, @Body params : ReportData) : Response<BaseResponse>
}