package com.songil.songil.page_mycomment

import com.songil.songil.config.BaseResponse
import com.songil.songil.page_mycomment.models.ResponseGetMyWritableComment
import com.songil.songil.page_mycomment.models.ResponseGetMyWrittenComment
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MycommentRetrofitInterface {
    @GET("my-page/crafts/comments")
    suspend fun getMyWritableComment(@Query("page") page : Int, @Query("type") type : String = "available") : Response<ResponseGetMyWritableComment>

    @GET("my-page/crafts/comments")
    suspend fun getMyWrittenComment(@Query("page") page : Int, @Query("type") type : String = "written") : Response<ResponseGetMyWrittenComment>

    @DELETE("shop/crafts/comments/{commentIdx}")
    suspend fun deleteComment(@Path("commentIdx") commentIdx : Int) : Response<BaseResponse>
}