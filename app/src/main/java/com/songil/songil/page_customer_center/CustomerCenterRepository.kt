package com.songil.songil.page_customer_center

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_customer_center.models.ResponseGetFnq

class CustomerCenterRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(CustomerCenterRetrofitInterface::class.java)

    suspend fun getFnq() : ResponseGetFnq {
        val result = retrofit.getFnq()
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}