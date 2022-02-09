package com.example.songil.page_shop

import com.example.songil.config.GlobalApplication
import com.example.songil.page_shop.models.ResponseShopMain
import retrofit2.Response

class ShopMainRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ShopMainRetrofitInterface::class.java)

    suspend fun getShopMainData() : Response<ResponseShopMain>{
        val result = retrofitInterface.getShopMain()
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}