package com.example.songil.page_story

import com.example.songil.config.GlobalApplication

class StoryRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(StoryRetrofitInterface::class.java)

    suspend fun getStoryDetail(storyIdx : Int) = retrofitInterface.getStoryDetail(storyIdx)
}