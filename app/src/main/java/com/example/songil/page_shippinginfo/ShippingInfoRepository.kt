package com.example.songil.page_shippinginfo

import com.example.songil.config.GlobalApplication
import com.example.songil.data.WaybillInfo

class ShippingInfoRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ShippingInfoRetrofitInterface::class.java)

    suspend fun postSendingInfo(orderDetailIdx : Int, year : Int, month : Int, day : Int, tCode : String, tInvoice : String) : Int {
        val result = retrofit.postSendingInfo(orderDetailIdx = orderDetailIdx, WaybillInfo(year = year, month = month, day = day, tCode =  tCode, tInvoice = tInvoice))
        if (result.isSuccessful) return result.body()!!.code
        else throw UnknownError()
    }
}