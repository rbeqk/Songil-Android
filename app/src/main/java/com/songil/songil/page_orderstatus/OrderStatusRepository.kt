package com.songil.songil.page_orderstatus

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_orderstatus.models.OrderStatusInfo

class OrderStatusRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(OrderStatusRetrofitInterface::class.java)

    suspend fun getOrderStatus(pageIdx : Int) : ArrayList<OrderStatusInfo> {
        val result = retrofit.getOrderStatus(pageIdx)
        if (result.isSuccessful) return result.body()!!.result
        else throw UnknownError()
    }
}