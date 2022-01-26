package com.example.songil.page_abtest

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.page_abtest.model.RequestVote
import retrofit2.Response

class AbtestVoteRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(AbtestVoteRetrofitInterface::class.java)

    suspend fun vote(abTestIdx : Int, vote : String) : Response<BaseResponse> {
        val result = retrofitInterface.postVote(abTestIdx, RequestVote(vote))
        if (result.isSuccessful) return result
        else throw UnknownError()

    }

    suspend fun cancelVote(abTestIdx : Int) : Response<BaseResponse> {
        val result = retrofitInterface.deleteVote(abTestIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

}