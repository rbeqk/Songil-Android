package com.example.songil.page_commentwrite

import com.example.songil.config.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface CommentWriteRetrofitInterface {
    @Multipart
    @POST("my-page/crafts/comments")
    suspend fun postComment(@PartMap data : HashMap<String, RequestBody>, @Part image : ArrayList<MultipartBody.Part>) : Response<BaseResponse>
}