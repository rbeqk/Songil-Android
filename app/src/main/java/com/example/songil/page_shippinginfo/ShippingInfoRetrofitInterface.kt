package com.example.songil.page_shippinginfo

import com.example.songil.config.BaseResponse
import com.example.songil.data.WaybillInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ShippingInfoRetrofitInterface {
    @POST("artist-page/ordrers/{orderDetailIdx}/sending")
    suspend fun postSendingInfo(@Path("orderDetailIdx") orderDetailIdx : Int, @Body params : WaybillInfo) : Response<BaseResponse>
}