package com.songil.songil.page_delivery

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_delivery.models.ResponseGetDeliveryTracking

class DeliveryRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(DeliveryRetrofitInterface::class.java)

    suspend fun getTrackingData(orderDetailIdx : Int) : ResponseGetDeliveryTracking{
        val result = retrofitInterface.getDeliveryTracking(orderDetailIdx)
        if (result.isSuccessful) return result.body()!!
        else throw  UnknownError()
    }
}