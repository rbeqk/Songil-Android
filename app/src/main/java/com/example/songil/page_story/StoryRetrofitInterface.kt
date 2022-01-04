package com.example.songil.page_story

import com.example.songil.page_story.models.ResponseStoryDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryRetrofitInterface {
    @GET("with/stories/{storyIdx}")
    suspend fun getStoryDetail(@Path("storyIdx") storyIdx : Int) : Response<ResponseStoryDetail>
}