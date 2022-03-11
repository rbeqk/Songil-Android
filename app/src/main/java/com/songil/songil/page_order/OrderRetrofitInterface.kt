package com.songil.songil.page_order

import com.songil.songil.config.BaseResponse
import com.songil.songil.page_order.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderRetrofitInterface {
    @POST("orders/crafts")
    suspend fun postOrderCrafts(@Body params : RequestBodyGetOrder) : Response<ResponseGetOrder>

    @POST("orders/{orderIdx}/extra-fee")
    suspend fun postOrderExtraFee(@Path("orderIdx") orderIdx : Int, @Body params : RequestBodyPostExtraFee) : Response<ResponsePostExtraFee>

    @POST("orders/{orderIdx}/benefits")
    suspend fun postBenefit(@Path("orderIdx") orderIdx :Int, @Body params : RequestBodyPostBenefit) : Response<ResponsePostBenefit>

    @POST("orders/{orderIdx}/etc-info")
    suspend fun postOrderEtcInfo(@Path("orderIdx") orderIdx :Int, @Body params : RequestBodyPostEtcInfo) : Response<ResponsePostEtcInfo>

    @POST("orders/{orderIdx}")
    suspend fun postOrderVerification(@Path("orderIdx") orderIdx : Int, @Body params : RequestBodyPostOrderVerification) : Response<BaseResponse>
}