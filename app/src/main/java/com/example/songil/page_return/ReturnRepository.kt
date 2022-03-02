package com.example.songil.page_return

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.page_return.models.RequestBodyPostReturn

class ReturnRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ReturnRetrofitInterface::class.java)

    suspend fun postReturnRequest(orderDetailIdx : Int, reasonIdx : Int, etcReason : String?) : BaseResponse {
        val result = retrofit.postReturnRequest(orderDetailIdx = orderDetailIdx, params = RequestBodyPostReturn(reasonIdx, etcReason))
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}