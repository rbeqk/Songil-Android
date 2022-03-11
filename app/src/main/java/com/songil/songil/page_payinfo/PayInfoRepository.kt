package com.songil.songil.page_payinfo

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_payinfo.models.ResponseGetOrderDetailInfo

class PayInfoRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(PayInfoRetrofitInterface::class.java)

    suspend fun getOrderDetailInfo(orderDetailIdx : Int) : ResponseGetOrderDetailInfo {
        val result = retrofit.getOrderDetailInfo(orderDetailIdx)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun getOrderDetailInfoArtist(orderDetailIdx : Int) : ResponseGetOrderDetailInfo {
        val result = retrofit.getOrderDetailInfoArtist(orderDetailIdx)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}