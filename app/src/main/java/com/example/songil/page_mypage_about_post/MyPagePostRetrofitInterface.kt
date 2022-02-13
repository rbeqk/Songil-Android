package com.example.songil.page_mypage_about_post

import com.example.songil.page_mypage_about_post.models.ResponseMyPagePost
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPagePostRetrofitInterface {
    @GET("my-page/with/liked")
    suspend fun getMyFavoritePost(@Query("page") page : Int) : Response<ResponseMyPagePost>

    @GET("my-page/with/comments")
    suspend fun getMyCommentPost(@Query("page") page : Int) : Response<ResponseMyPagePost>

    @GET("my-page/with")
    suspend fun getMyPost(@Query("page") page : Int) : Response<ResponseMyPagePost>
}