package com.example.songil.page_basket

import com.example.songil.page_basket.models.ResponseCarts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BasketRetrofitInterface {
    @GET("carts")
    suspend fun getCarts(@Query("userIdx") userIdx : Int) : Response<ResponseCarts>
}