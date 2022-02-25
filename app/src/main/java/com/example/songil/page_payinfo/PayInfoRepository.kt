package com.example.songil.page_payinfo

import com.example.songil.config.GlobalApplication
import com.example.songil.page_payinfo.models.ResponseGetOrderDetailInfo

class PayInfoRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(PayInfoRetrofitInterface::class.java)

    suspend fun getOrderDetailInfo(orderDetailIdx : Int) : ResponseGetOrderDetailInfo {
        val result = retrofit.getOrderDetailInfo(orderDetailIdx)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}