package com.example.songil.page_abtest

import com.example.songil.config.BaseResponse
import com.example.songil.page_abtest.model.RequestWriteComment
import com.example.songil.page_abtest.model.ResponseAbtest
import com.example.songil.page_abtest.model.ResponseAbtestChat
import retrofit2.Response
import retrofit2.http.*

interface AbtestRetrofitInterface {
    @GET("with/ab-test/{abTestIdx}/comments")
    suspend fun getComments(@Path("abTestIdx") abTestIdx : Int, @Query("page") page : Int) : Response<ResponseAbtestChat>

    @GET("with/ab-test/{abTestIdx}")
    suspend fun getAbtest(@Path("abTestIdx") abTestIdx : Int) : Response<ResponseAbtest>

    @POST("with/ab-test/{abTestIdx}/comments")
    suspend fun postAbtestChat(@Path("abTestIdx") abTestIdx : Int, @Body params : RequestWriteComment) : Response<BaseResponse>

    @DELETE("with/ab-test/ocomments/{commentIdx}")
    suspend fun deleteAbtestChat(@Path("commentIdx") commentIdx : Int) : Response<BaseResponse>
}