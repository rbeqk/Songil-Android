package com.example.songil.page_story.subpage_story_chat

import com.example.songil.page_story.subpage_story_chat.models.ResponseStoryChat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoryChatRetrofitInterface {
    @GET("with/stories/{storyIdx}/comments")
    suspend fun getStoryChat(@Path("storyIdx") storyIdx : Int, @Query("page") page : Int) : Response<ResponseStoryChat>
}