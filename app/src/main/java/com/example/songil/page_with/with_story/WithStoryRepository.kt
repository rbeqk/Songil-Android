package com.example.songil.page_with.with_story

import com.example.songil.config.GlobalApplication
import com.example.songil.data.FrontStory

class WithStoryRepository {

    private val retrofitInterface = GlobalApplication.sRetrofit.create(WithStoryRetrofitInterface::class.java)

    suspend fun getStoryPageIdx() = retrofitInterface.getStoryPageIdx("story")

    suspend fun getStory(pageIdx : Int, sort : String) : ArrayList<FrontStory> {
        if (pageIdx >= 1){
            retrofitInterface.getStory("story", sort, pageIdx).let { response ->
                if (response.isSuccessful){
                    return response.body()!!.result.story
                }
            }
            throw UnknownError()
        }
        return arrayListOf()
    }
}