package com.example.songil.page_artistmanage.subpage_answer

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.page_artistmanage.subpage_answer.models.RequestBodyPostAsk
import com.example.songil.page_artistmanage.subpage_answer.models.ResponseGetAskDetail
import retrofit2.Response

class ArtistManageAnswerRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ArtistManageAnswerRetrofitInterface::class.java)

    suspend fun getAskDetailInfo(askIdx : Int) : Response<ResponseGetAskDetail>{
        val result = retrofit.getAskDetail(askIdx = askIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun sendAnswer(askIdx : Int, content : String) : Response<BaseResponse> {
        val result = retrofit.postAsk(askIdx = askIdx, params = RequestBodyPostAsk(content))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}