package com.example.songil.page_basket

import com.example.songil.config.BaseResponse
import com.example.songil.page_basket.models.RequestDeleteItem
import com.example.songil.page_basket.models.RequestFixCarts
import com.example.songil.page_basket.models.ResponseCartItems
import retrofit2.Response
import retrofit2.http.*

interface BasketRetrofitInterface {
    @GET("carts")
    suspend fun getCarts(@Query("userIdx") userIdx : Int) : Response<ResponseCartItems>

    @PATCH("carts")
    suspend fun patchCarts(@Body params : RequestFixCarts) : Response<BaseResponse>

    @HTTP(method = "DELETE", hasBody = true, path = "carts")
    suspend fun deleteCartItem(@Body params: RequestDeleteItem) : Response<BaseResponse>


}