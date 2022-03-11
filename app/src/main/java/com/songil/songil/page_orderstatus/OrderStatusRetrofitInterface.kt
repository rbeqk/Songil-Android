package com.songil.songil.page_orderstatus

import com.songil.songil.page_orderstatus.models.ResponseGetOrderStatus
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderStatusRetrofitInterface {
    @GET("my-page/orders")
    suspend fun getOrderStatus(@Query("page") page : Int) : Response<ResponseGetOrderStatus>
}