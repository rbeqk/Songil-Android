package com.example.songil.page_order

import com.example.songil.page_order.models.RequestBodyGetOrder
import com.example.songil.page_order.models.RequestBodyPostExtraFee
import com.example.songil.page_order.models.ResponseGetOrder
import com.example.songil.page_order.models.ResponsePostExtraFee
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderRetrofitInterface {
    @POST("orders/crafts")
    suspend fun postOrderCrafts(@Body params : RequestBodyGetOrder) : Response<ResponseGetOrder>

    @POST("orders/{orderIdx}/extra-fee")
    suspend fun postOrderExtraFee(@Path("orderIdx") orderIdx : Int, @Body params : RequestBodyPostExtraFee) : Response<ResponsePostExtraFee>
}