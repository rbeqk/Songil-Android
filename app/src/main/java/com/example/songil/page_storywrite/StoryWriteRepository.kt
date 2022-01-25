package com.example.songil.page_storywrite

import com.example.songil.config.GlobalApplication
import com.example.songil.page_storywrite.models.ResponseUploadStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class StoryWriteRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(StoryWriteRetrofitInterface::class.java)

    suspend fun uploadStory(data : HashMap<String, RequestBody>, tag : List<MultipartBody.Part>, image : ArrayList<MultipartBody.Part>) : Response<ResponseUploadStory> {
        val result = retrofitInterface.postStories(data, tag, image)
        if (result.isSuccessful) return result
        throw UnknownError()
    }

    suspend fun modifyStory(storyIdx : Int, data : HashMap<String, RequestBody>, tag : List<MultipartBody.Part>, image : ArrayList<MultipartBody.Part>) : Response<ResponseUploadStory> {
        val result = retrofitInterface.patchStories(storyIdx, data, tag, image)
        if (result.isSuccessful) return result
        throw UnknownError()
    }
}