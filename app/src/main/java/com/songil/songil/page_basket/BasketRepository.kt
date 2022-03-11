package com.songil.songil.page_basket

import com.songil.songil.config.BaseResponse
import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_basket.models.RequestBodyChangeAmount
import com.songil.songil.page_basket.models.ResponseGetCartItem
import com.songil.songil.page_basket.models.ResponsePatchCartItem
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