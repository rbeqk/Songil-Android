package com.example.songil.page_mybenefit

import com.example.songil.page_mybenefit.models.ResponseMyBenefit
import retrofit2.Response
import retrofit2.http.GET

interface MybenefitRetrofitInterface {
    @GET("my-page/benefits")
    suspend fun getMyBenefits() : Response<ResponseMyBenefit>
}