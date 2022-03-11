package com.songil.songil.page_mybenefit

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_mybenefit.models.ResponseMyBenefit
import retrofit2.Response

class MybenefitRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(MybenefitRetrofitInterface::class.java)

    suspend fun getMyBenefit() : Response<ResponseMyBenefit>{
        val result = retrofit.getMyBenefits()
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}