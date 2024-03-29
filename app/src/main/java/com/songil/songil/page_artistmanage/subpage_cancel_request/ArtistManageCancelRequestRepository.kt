package com.songil.songil.page_artistmanage.subpage_cancel_request

import com.songil.songil.config.BaseResponse
import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_artistmanage.subpage_cancel_request.models.RequestBodyPostCancelOrReturn
import com.songil.songil.page_artistmanage.subpage_cancel_request.models.ResponseGetCancelRequestItemList
import com.songil.songil.page_artistmanage.subpage_cancel_request.models.ResponseGetCancelRequestPage

class ArtistManageCancelRequestRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ArtistManageCancelRequestRetrofitInterface::class.java)

    suspend fun getCancelRequestPage() : ResponseGetCancelRequestPage {
        val result = retrofit.getCancelRequestPageCnt()
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun getCancelRequestItemList(page : Int) : ResponseGetCancelRequestItemList {
        val result = retrofit.getCancelRequestItems(page = page)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun postCancelRequestAnswer(orderDetailIdx : Int, type : String) : BaseResponse {
        val result = retrofit.postCancelOrder(orderDetailIdx, RequestBodyPostCancelOrReturn(type))
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun postReturnRequestAnswer(orderDetailIdx: Int, type : String) : BaseResponse {
        val result = retrofit.postReturnOrder(orderDetailIdx, RequestBodyPostCancelOrReturn(type))
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}