package com.example.songil.page_story.subpage_story_chat

import com.example.songil.config.BaseResponse
import com.example.songil.page_story.subpage_story_chat.models.RequestWriteComment
import com.example.songil.page_story.subpage_story_chat.models.ResponseStoryChat
import retrofit2.Response
import retrofit2.http.*

interface StoryChatRetrofitInterface {
    @GET("with/stories/{storyIdx}/comments")
    suspend fun getStoryChat(@Path("storyIdx") storyIdx : Int, @Query("page") page : Int) : Response<ResponseStoryChat>

    @POST("with/stories/{storyIdx}/comments")
    suspend fun postStoryChat(@Path("storyIdx") storyIdx: Int, @Body params : RequestWriteComment) : Response<BaseResponse>

    @DELETE("with/stories/comments/{commentIdx}")
    suspend fun deleteStoryChat(@Path("commentIdx") commentIdx : Int) : Response<BaseResponse>
}