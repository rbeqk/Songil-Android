package com.songil.songil.page_story

import com.songil.songil.config.BaseResponse
import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_story.models.ResponseStoryDetail
import com.songil.songil.page_story.models.ResponseStoryLike
import retrofit2.Response

class StoryRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(StoryRetrofitInterface::class.java)

    suspend fun getStoryDetail(storyIdx : Int) : Response<ResponseStoryDetail> {
        val result = retrofitInterface.getStoryDetail(storyIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun getStoryLike(storyIdx : Int) : Response<ResponseStoryLike> {
        val result = retrofitInterface.patchStoryLike(storyIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun delStory(storyIdx : Int) : Response<BaseResponse> {
        val result = retrofitInterface.deleteStory(storyIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}