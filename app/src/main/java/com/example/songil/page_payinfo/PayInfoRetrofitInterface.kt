package com.example.songil.page_payinfo

import com.example.songil.page_payinfo.models.ResponseGetOrderDetailInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PayInfoRetrofitInterface {
    @GET("my-page/orders/{orderDetailIdx}")
    suspend fun getOrderDetailInfo(@Path("orderDetailIdx") orderDetailIdx : Int) : Response<ResponseGetOrderDetailInfo>

    @GET("artist-page/orders/{orderDetailIdx}")
    suspend fun getOrderDetailInfoArtist(@Path("orderDetailIdx") orderDetailIdx : Int) : Response<ResponseGetOrderDetailInfo>
}