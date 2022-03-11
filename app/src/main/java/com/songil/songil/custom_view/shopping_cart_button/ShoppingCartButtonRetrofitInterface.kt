package com.songil.songil.custom_view.shopping_cart_button

import com.songil.songil.custom_view.shopping_cart_button.models.ResponseGetCartItemCount
import retrofit2.Response
import retrofit2.http.GET

interface ShoppingCartButtonRetrofitInterface {
    @GET("cart/count")
    suspend fun getCartItemCount() : Response<ResponseGetCartItemCount>
}