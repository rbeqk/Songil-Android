package com.songil.songil.page_with

import com.songil.songil.config.BaseResponse
import com.songil.songil.page_with.models.ResponseHotTalk
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WithRetrofitInterface {
    @GET("with/hot-talk")
    suspend fun getHotTalk() : Response<ResponseHotTalk>

    @POST("with/users/{userIdx}/block")
    suspend fun postBlockUser(@Path("userIdx") userIdx : Int) : Response<BaseResponse>
}