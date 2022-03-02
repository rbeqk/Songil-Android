package com.example.songil.page_search

import com.example.songil.config.BaseResponse
import com.example.songil.page_search.models.*
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

    @GET("search/page")
    suspend fun getSearchResultPage(@Query("keyword") keyword : String, @Query("category") category : String) : Response<ResponseGetSearchResultPage>

    @GET("search")
    suspend fun getSearchShopList(@Query("keyword") keyword : String, @Query("sort") sort : String, @Query("page") page : Int, @Query("category") category : String = "shop") : Response<ResponseGetSearchShopList>

    @GET("search")
    suspend fun getSearchWithList(@Query("keyword") keyword : String, @Query("sort") sort : String, @Query("page") page : Int, @Query("category") category : String = "with") : Response<ResponseGetSearchWithList>

    @GET("search")
    suspend fun getSearchArticleList(@Query("keyword") keyword : String, @Query("sort") sort : String, @Query("page") page : Int, @Query("category") category : String = "article") : Response<ResponseGetSearchArticleList>


}