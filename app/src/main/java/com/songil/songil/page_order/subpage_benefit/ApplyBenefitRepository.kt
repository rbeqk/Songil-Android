package com.songil.songil.page_order.subpage_benefit

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_mybenefit.models.ResponseMyBenefit
import retrofit2.Response

class ApplyBenefitRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ApplyBenefitRetrofitInterface::class.java)

    suspend fun getBenefit(orderIdx : Int) : Response<ResponseMyBenefit>{
        val result = retrofit.getBenefit(orderIdx = orderIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}