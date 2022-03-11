package com.songil.songil.page_with

import com.songil.songil.page_with.models.ResponseHotTalk
import retrofit2.Response
import retrofit2.http.GET

interface WithRetrofitInterface {
    @GET("with/hot-talk")
    suspend fun getHotTalk() : Response<ResponseHotTalk>
}