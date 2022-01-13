package com.example.songil.page_with.with_story

import com.example.songil.page_with.with_story.models.ResponseStory
import com.example.songil.page_with.with_story.models.ResponseStoryPageIdx
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WithStoryRetrofitInterface {
    @GET("with/page")
    suspend fun getStoryPageIdx(@Query("category") category : String) : Response<ResponseStoryPageIdx>

    @GET("with/")
    suspend fun getStory(@Query("category") category : String, @Query("sort") sort : String, @Query("page") page : Int) : Response<ResponseStory>
}