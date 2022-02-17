package com.example.songil.page_craft

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.page_basket.models.Amount
import com.example.songil.page_craft.models.ResponseCraftLike
import retrofit2.Response

class CraftRepository {
    private val craftRetrofitInterface = GlobalApplication.sRetrofit.create(CraftRetrofitInterface::class.java)

    suspend fun getDetailCraftInfo(craftIdx : Int) = craftRetrofitInterface.getProducts(craftIdx)

    suspend fun addToCart(craftIdx : Int, amount : Int) : Response<BaseResponse>  {
        val result = craftRetrofitInterface.postCraftItem(craftIdx, Amount(amount))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun toggleLike(craftIdx : Int) : Response<ResponseCraftLike> {
        val result = craftRetrofitInterface.patchCraftLike(craftIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}