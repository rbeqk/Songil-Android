package com.example.songil.page_order

import com.example.songil.config.GlobalApplication
import com.example.songil.data.CraftAndAmount
import com.example.songil.page_order.models.*
import retrofit2.Response

class OrderRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(OrderRetrofitInterface::class.java)

    suspend fun getOrderData(crafts : ArrayList<CraftAndAmount>) : Response<ResponseGetOrder> {
        val result = retrofit.postOrderCrafts(params = RequestBodyGetOrder(crafts))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun postExtraFee(orderIdx : Int, zipcode : String) : Response<ResponsePostExtraFee> {
        val result = retrofit.postOrderExtraFee(orderIdx, RequestBodyPostExtraFee(zipcode))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun postBenefit(orderIdx : Int, benefitIdx : Int?) : Response<ResponsePostBenefit>{
        val result = retrofit.postBenefit(orderIdx = orderIdx, params = RequestBodyPostBenefit(benefitIdx))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}