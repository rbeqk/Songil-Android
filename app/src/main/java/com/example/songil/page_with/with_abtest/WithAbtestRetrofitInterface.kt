package com.example.songil.page_with.with_abtest

import com.example.songil.page_with.with_abtest.models.ResponseAbTestPageCnt
import com.example.songil.page_with.with_abtest.models.ResponseGetAbTest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WithAbtestRetrofitInterface {
    @GET("with")
    suspend fun getAbTest(@Query("category") category : String, @Query("sort") sort : String, @Query("page") page : Int) : Response<ResponseGetAbTest>

    @GET("with/page")
    suspend fun getAbTestPageCnt(@Query("category") category : String = "ab-test") : Response<ResponseAbTestPageCnt>
}