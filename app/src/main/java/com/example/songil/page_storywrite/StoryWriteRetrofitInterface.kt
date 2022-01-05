package com.example.songil.page_storywrite

import com.example.songil.page_storywrite.models.ResponseUploadStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface StoryWriteRetrofitInterface {

    @Multipart
    @POST("with/stories")
    suspend fun postStories(@PartMap data : HashMap<String, RequestBody>, @Part tag : List<MultipartBody.Part>,
                             @Part image : ArrayList<MultipartBody.Part>) : Response<ResponseUploadStory>
}