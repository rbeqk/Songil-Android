package com.example.songil.page_customer_center

import com.example.songil.config.GlobalApplication
import com.example.songil.page_customer_center.models.ResponseGetFnq

class CustomerCenterRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(CustomerCenterRetrofitInterface::class.java)

    suspend fun getFnq() : ResponseGetFnq {
        val result = retrofit.getFnq()
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}