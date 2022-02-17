package com.example.songil.page_basket

import com.example.songil.config.BaseResponse
import com.example.songil.page_basket.models.RequestBodyChangeAmount
import com.example.songil.page_basket.models.ResponseGetCartItem
import com.example.songil.page_basket.models.ResponsePatchCartItem
import retrofit2.Response
import retrofit2.http.*

interface BasketRetrofitInterface {
    @GET("cart")
    suspend fun getCartItem() : Response<ResponseGetCartItem>

    @PATCH("cart/crafts/{craftIdx}")
    suspend fun patchCartItem(@Path("craftIdx") craftIdx : Int, @Body params : RequestBodyChangeAmount) : Response<ResponsePatchCartItem>

    @DELETE("cart/crafts/{craftIdx}")
    suspend fun deleteCartItem(@Path("craftIdx") craftIdx : Int) : Response<BaseResponse>
}