package com.songil.songil.page_story

import com.songil.songil.config.BaseResponse
import com.songil.songil.page_story.models.ResponseStoryDetail
import com.songil.songil.page_story.models.ResponseStoryLike
import retrofit2.Response
import retrofit2.http.*

interface StoryRetrofitInterface {
    @GET("with/stories/{storyIdx}")
    suspend fun getStoryDetail(@Path("storyIdx") storyIdx : Int) : Response<ResponseStoryDetail>

    @PATCH("with/stories/{storyIdx}/like")
    suspend fun patchStoryLike(@Path("storyIdx") storyIdx : Int) : Response<ResponseStoryLike>

    @DELETE("with/stories/{storyIdx}")
    suspend fun deleteStory(@Path("storyIdx") storyIdx : Int) : Response<BaseResponse>
}