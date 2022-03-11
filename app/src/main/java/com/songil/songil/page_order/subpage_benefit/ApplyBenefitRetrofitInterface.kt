package com.songil.songil.page_order.subpage_benefit

import com.songil.songil.page_mybenefit.models.ResponseMyBenefit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApplyBenefitRetrofitInterface {
    @GET("orders/{orderIdx}/benefits")
    suspend fun getBenefit(@Path("orderIdx") orderIdx : Int) : Response<ResponseMyBenefit> // in page_mybenefit/Response.kt
}