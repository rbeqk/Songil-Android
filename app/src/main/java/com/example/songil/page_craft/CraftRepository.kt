package com.example.songil.page_craft

import com.example.songil.config.GlobalApplication

class CraftRepository {
    private val craftRetrofitInterface = GlobalApplication.sRetrofit.create(CraftRetrofitInterface::class.java)

    suspend fun getDetailCraftInfo(craftIdx : Int) = craftRetrofitInterface.getProducts(craftIdx)
}