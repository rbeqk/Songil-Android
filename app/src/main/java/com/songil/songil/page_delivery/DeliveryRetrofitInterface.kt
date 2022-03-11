package com.songil.songil.page_delivery

import com.songil.songil.page_delivery.models.ResponseGetDeliveryTracking
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DeliveryRetrofitInterface {
    @GET("my-page/orders/{orderDetailIdx}/tracking")
    suspend fun getDeliveryTracking(@Path("orderDetailIdx") orderDetailIdx : Int) : Response<ResponseGetDeliveryTracking>
}