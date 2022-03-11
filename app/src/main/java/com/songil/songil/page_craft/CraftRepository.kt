package com.songil.songil.page_craft

import com.songil.songil.config.BaseResponse
import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_basket.models.Amount
import com.songil.songil.page_craft.models.ResponseCraftDetail
import com.songil.songil.page_craft.models.ResponseCraftLike
import retrofit2.Response

class CraftRepository {
    private val craftRetrofitInterface = GlobalApplication.sRetrofit.create(CraftRetrofitInterface::class.java)

    suspend fun getDetailCraftInfo(craftIdx : Int) : ResponseCraftDetail {
        val result = craftRetrofitInterface.getProducts(craftIdx)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

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