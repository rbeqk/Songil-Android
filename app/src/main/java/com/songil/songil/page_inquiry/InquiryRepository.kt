package com.songil.songil.page_inquiry

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_inquiry.models.RequestBodyPostInquiry
import com.songil.songil.page_inquiry.models.ResponsePostInquiry
import retrofit2.Response

class InquiryRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(InquiryRetrofitInterface::class.java)

    suspend fun sendCraftInquiry(craftIdx : Int, content : String) : Response<ResponsePostInquiry> {
        val result = retrofit.postCraftInquiry(craftIdx = craftIdx, params = RequestBodyPostInquiry(content))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun sendOrderInquiry(orderDetailIdx : Int, content : String) : Response<ResponsePostInquiry> {
        val result = retrofit.postOrderInquiry(orderDetailIdx = orderDetailIdx, params = RequestBodyPostInquiry(content))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}