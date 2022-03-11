package com.songil.songil.page_customer_center

import com.songil.songil.page_customer_center.models.ResponseGetFnq
import retrofit2.Response
import retrofit2.http.GET

interface CustomerCenterRetrofitInterface {
    @GET("faq")
    suspend fun getFnq() : Response<ResponseGetFnq>
}