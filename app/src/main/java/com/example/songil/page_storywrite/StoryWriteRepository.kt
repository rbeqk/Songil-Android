package com.example.songil.page_storywrite

import com.example.songil.config.GlobalApplication
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryWriteRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(StoryWriteRetrofitInterface::class.java)

    suspend fun uploadStory(data : HashMap<String, RequestBody>, tag : List<MultipartBody.Part>, image : ArrayList<MultipartBody.Part>) = retrofitInterface.postStories(data, tag, image)
}