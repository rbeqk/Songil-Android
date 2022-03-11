package com.songil.songil.page_storywrite

import com.songil.songil.page_storywrite.models.ResponseUploadStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface StoryWriteRetrofitInterface {

    @Multipart
    @POST("with/stories")
    suspend fun postStories(@PartMap data : HashMap<String, RequestBody>, @Part tag : List<MultipartBody.Part>,
                             @Part image : ArrayList<MultipartBody.Part>) : Response<ResponseUploadStory>

    @Multipart
    @PATCH("with/stories/{storyIdx}")
    suspend fun patchStories(@Path("storyIdx") storyIdx : Int, @PartMap data : HashMap<String, RequestBody>, @Part tag : List<MultipartBody.Part>,
                             @Part image : ArrayList<MultipartBody.Part>) : Response<ResponseUploadStory>
}