package com.songil.songil.page_shop

import com.songil.songil.page_shop.models.ResponseShopMain
import retrofit2.Response
import retrofit2.http.GET

interface ShopMainRetrofitInterface {
    @GET("shop")
    suspend fun getShopMain() : Response<ResponseShopMain>
}