package com.example.songil.page_mypage_about_post

import com.example.songil.config.GlobalApplication
import com.example.songil.page_mypage_about_post.models.ResponseMyPagePost
import retrofit2.Response

class MyPagePostRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(MyPagePostRetrofitInterface::class.java)

    suspend fun getMyFavoritePost(page : Int) : Response<ResponseMyPagePost> {
        val result = retrofit.getMyFavoritePost(page = page)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun getMyCommentPost(page : Int) : Response<ResponseMyPagePost> {
        val result = retrofit.getMyCommentPost(page = page)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun getMyPost(page : Int) : Response<ResponseMyPagePost> {
        val result = retrofit.getMyPost(page = page)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}