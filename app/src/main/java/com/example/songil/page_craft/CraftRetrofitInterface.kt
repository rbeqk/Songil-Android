package com.example.songil.page_craft

import com.example.songil.page_craft.models.ResponseProductDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CraftRetrofitInterface {
    @GET("products/{productIdx}")
    suspend fun getProducts(@Path("productIdx") productIdx : Int) : Response<ResponseProductDetail>
}