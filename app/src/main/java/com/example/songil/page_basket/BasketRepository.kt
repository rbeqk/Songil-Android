package com.example.songil.page_basket

import com.example.songil.config.GlobalApplication
import com.example.songil.page_basket.models.RequestDeleteItem
import com.example.songil.page_basket.models.RequestFixCarts

class BasketRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(BasketRetrofitInterface::class.java)

    suspend fun getCartItems() = retrofitInterface.getCarts(GlobalApplication.globalSharedPreferences.getInt(GlobalApplication.USER_IDX, 0))

    suspend fun changeItemCount(craftIdx : Int, amount : Int) = retrofitInterface.patchCarts(RequestFixCarts(craftIdx, amount))

    suspend fun deleteItem(craftIdx : Int) = retrofitInterface.deleteCartItem(RequestDeleteItem(craftIdx))
}