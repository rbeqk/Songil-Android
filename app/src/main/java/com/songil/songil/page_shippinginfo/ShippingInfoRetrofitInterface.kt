package com.songil.songil.page_shippinginfo

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.WaybillInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ShippingInfoRetrofitInterface {
    @POST("artist-page/orders/{orderDetailIdx}/sending")
    suspend fun postSendingInfo(@Path("orderDetailIdx") orderDetailIdx : Int, @Body params : WaybillInfo) : Response<BaseResponse>
}