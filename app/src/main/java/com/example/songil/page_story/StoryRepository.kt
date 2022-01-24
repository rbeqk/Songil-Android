package com.example.songil.page_story

import com.example.songil.config.GlobalApplication
import com.example.songil.data.LikeData

class StoryRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(StoryRetrofitInterface::class.java)

    suspend fun getStoryDetail(storyIdx : Int) = retrofitInterface.getStoryDetail(storyIdx)

    suspend fun getStoryLike(storyIdx : Int) : LikeData? {
        val result = retrofitInterface.patchStoryLike(storyIdx)
        return result.body()?.result
    }

    suspend fun delStory(storyIdx : Int) : Boolean {
        val result = retrofitInterface.deleteStory(storyIdx)
        return (result.body()?.code == 200)
    }
}