package com.example.songil.page_commentwrite

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CommentWriteRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(CommentWriteRetrofitInterface::class.java)

    suspend fun uploadComment(data : HashMap<String, RequestBody>, image : ArrayList<MultipartBody.Part>) : BaseResponse {
        val result = retrofitInterface.postComment(data = data, image = image)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}