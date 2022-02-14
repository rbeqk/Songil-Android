package com.example.songil.page_inquiry

import com.example.songil.config.GlobalApplication
import com.example.songil.page_inquiry.models.RequestBodyPostInquiry
import com.example.songil.page_inquiry.models.ResponsePostInquiry
import retrofit2.Response

class InquiryRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(InquiryRetrofitInterface::class.java)

    suspend fun sendInquiry(craftIdx : Int, content : String) : Response<ResponsePostInquiry> {
        val result = retrofit.postInquiry(craftIdx = craftIdx, params = RequestBodyPostInquiry(content))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}