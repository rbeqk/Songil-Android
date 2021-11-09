package com.example.songil.page_basket

import com.example.songil.config.GlobalApplication

class BasketRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(BasketRetrofitInterface::class.java)

    suspend fun getCartItems() = retrofitInterface.getCarts(GlobalApplication.globalSharedPreferences.getInt(GlobalApplication.USER_IDX, 0))
}