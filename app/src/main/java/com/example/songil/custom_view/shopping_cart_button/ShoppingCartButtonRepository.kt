package com.example.songil.custom_view.shopping_cart_button

import com.example.songil.config.GlobalApplication
import com.example.songil.custom_view.shopping_cart_button.models.ResponseGetCartItemCount
import retrofit2.Response

class ShoppingCartButtonRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ShoppingCartButtonRetrofitInterface::class.java)

    suspend fun getCartItemCnt() : Response<ResponseGetCartItemCount> {
        val result = retrofit.getCartItemCount()
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}