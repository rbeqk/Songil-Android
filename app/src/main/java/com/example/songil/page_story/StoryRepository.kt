package com.example.songil.page_story

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.data.LikeData
import com.example.songil.page_story.models.ResponseStoryLike
import retrofit2.Response

class StoryRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(StoryRetrofitInterface::class.java)

    suspend fun getStoryDetail(storyIdx : Int) = retrofitInterface.getStoryDetail(storyIdx)

    suspend fun getStoryLike(storyIdx : Int) : Response<ResponseStoryLike> {
        val result = retrofitInterface.patchStoryLike(storyIdx)
        if (result.isSuccessful) return result
        throw UnknownError()
    }

    suspend fun delStory(storyIdx : Int) : Response<BaseResponse> {
        val result = retrofitInterface.deleteStory(storyIdx)
        if (result.isSuccessful) return result
        throw UnknownError()
    }
}