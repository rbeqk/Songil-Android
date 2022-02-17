package com.example.songil.page_basket

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.page_basket.models.RequestBodyChangeAmount
import com.example.songil.page_basket.models.ResponseGetCartItem
import com.example.songil.page_basket.models.ResponsePatchCartItem
import retrofit2.Response

class BasketRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(BasketRetrofitInterface::class.java)

    suspend fun getCartItem() : Response<ResponseGetCartItem> {
        val result =retrofitInterface.getCartItem()
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun changeItemAmount(craftIdx : Int, amountChange : Int) : Response<ResponsePatchCartItem> {
        val result = retrofitInterface.patchCartItem(craftIdx = craftIdx, params = RequestBodyChangeAmount(amountChange))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun deleteItem(craftIdx : Int) : Response<BaseResponse> {
        val result = retrofitInterface.deleteCartItem(craftIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}