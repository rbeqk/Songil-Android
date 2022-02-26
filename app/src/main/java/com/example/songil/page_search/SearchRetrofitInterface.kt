package com.example.songil.page_search

import com.example.songil.config.BaseResponse
import com.example.songil.page_search.models.ResponseGetSearchKeywords
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofitInterface {
    @GET("search/keywords")
    suspend fun getSearchKeywords() : Response<ResponseGetSearchKeywords>

    @DELETE("search/all")
    suspend fun deleteAllRecentlyKeywords() : Response<BaseResponse>

    @DELETE("search")
    suspend fun deleteKeywords(@Query("word") word : String) : Response<BaseResponse>
}