package com.example.songil.page_abtest

import com.example.songil.config.BaseResponse
import com.example.songil.page_abtest.model.RequestVote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface AbtestVoteRetrofitInterface {
    @DELETE("with/ab-test/{abTestIdx}/vote")
    suspend fun deleteVote(@Path("abTestIdx") abTestIdx : Int) : Response<BaseResponse>

    @POST("with/ab-test/{abTestIdx}/vote")
    suspend fun postVote(@Path("abTestIdx") abTestIdx : Int, @Body params : RequestVote) : Response<BaseResponse>
}