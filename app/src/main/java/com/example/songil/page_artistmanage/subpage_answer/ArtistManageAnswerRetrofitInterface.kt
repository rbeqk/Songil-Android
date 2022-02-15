package com.example.songil.page_artistmanage.subpage_answer

import com.example.songil.config.BaseResponse
import com.example.songil.page_artistmanage.subpage_answer.models.RequestBodyPostAsk
import com.example.songil.page_artistmanage.subpage_answer.models.ResponseGetAskDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ArtistManageAnswerRetrofitInterface {
    @GET("artist-page/ask/{askIdx}")
    suspend fun getAskDetail(@Path("askIdx") askIdx : Int) : Response<ResponseGetAskDetail>

    @POST("artist-page/ask/{askIdx}")
    suspend fun postAsk(@Path("askIdx") askIdx : Int, @Body params : RequestBodyPostAsk) : Response<BaseResponse>
}