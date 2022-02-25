package com.example.songil.page_cancel

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.page_cancel.models.RequestBodyPostOrderCancel

class CancelRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(CancelRetrofitInterface::class.java)

    suspend fun requestOrderCancel(orderDetailIdx : Int, reasonIdx : Int, etcReason : String?) : BaseResponse {
        val result = retrofit.postOrderCancel(orderDetailIdx = orderDetailIdx, params = RequestBodyPostOrderCancel(reasonIdx, etcReason))
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}