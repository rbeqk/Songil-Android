package com.songil.songil.page_shippinginfo_confirmation

import com.songil.songil.page_shippinginfo_confirmation.models.ResponseGetShippingInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ShippingInfoConfirmationRetrofitInterface {
    @GET("/artist-page/orders/{orderDetailIdx}/sending")
    suspend fun getSendingInfo(@Path("orderDetailIdx") orderDetailIdx : Int) : Response<ResponseGetShippingInfo>
}