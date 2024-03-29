package com.songil.songil.page_abtest

import com.songil.songil.config.BaseResponse
import com.songil.songil.page_abtest.model.RequestWriteComment
import com.songil.songil.page_abtest.model.ResponseAbtest
import com.songil.songil.page_abtest.model.ResponseAbtestChat
import retrofit2.Response
import retrofit2.http.*

interface AbtestRetrofitInterface {
    @GET("with/ab-test/{abTestIdx}/comments")
    suspend fun getComments(@Path("abTestIdx") abTestIdx : Int, @Query("page") page : Int) : Response<ResponseAbtestChat>

    @GET("with/ab-test/{abTestIdx}")
    suspend fun getAbtest(@Path("abTestIdx") abTestIdx : Int) : Response<ResponseAbtest>

    @POST("with/ab-test/{abTestIdx}/comments")
    suspend fun postAbtestChat(@Path("abTestIdx") abTestIdx : Int, @Body params : RequestWriteComment) : Response<BaseResponse>

    @DELETE("with/ab-test/comments/{commentIdx}") // change ocomment to comment
    suspend fun deleteAbtestChat(@Path("commentIdx") commentIdx : Int) : Response<BaseResponse>

    @DELETE("with/ab-test/{abTestIdx}")
    suspend fun deleteAbTest(@Path("abTestIdx") abTestIdx : Int) : Response<BaseResponse>
}