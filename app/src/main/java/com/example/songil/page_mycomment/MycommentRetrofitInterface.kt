package com.example.songil.page_mycomment

import com.example.songil.page_mycomment.models.ResponseGetMyWritableComment
import com.example.songil.page_mycomment.models.ResponseGetMyWrittenComment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MycommentRetrofitInterface {
    @GET("my-page/crafts/comments")
    suspend fun getMyWritableComment(@Query("page") page : Int, @Query("type") type : String = "available") : Response<ResponseGetMyWritableComment>

    @GET("my-page/crafts/comments")
    suspend fun getMyWrittenComment(@Query("page") page : Int, @Query("type") type : String = "written") : Response<ResponseGetMyWrittenComment>

}