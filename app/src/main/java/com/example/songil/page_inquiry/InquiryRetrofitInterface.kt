package com.example.songil.page_inquiry

import com.example.songil.page_inquiry.models.RequestBodyPostInquiry
import com.example.songil.page_inquiry.models.ResponsePostInquiry
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface InquiryRetrofitInterface {
    @POST("shop/crafts/{craftIdx}/ask")
    suspend fun postInquiry(@Path("craftIdx") craftIdx : Int, @Body params : RequestBodyPostInquiry) : Response<ResponsePostInquiry>
}