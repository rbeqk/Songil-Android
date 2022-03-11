package com.songil.songil.page_shippinginfo_confirmation

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_shippinginfo_confirmation.models.ResponseGetShippingInfo

class ShippingInfoConfirmationRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ShippingInfoConfirmationRetrofitInterface::class.java)

    suspend fun getShippingInfo(orderDetailIdx : Int) : ResponseGetShippingInfo {
        val result = retrofit.getSendingInfo(orderDetailIdx)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}