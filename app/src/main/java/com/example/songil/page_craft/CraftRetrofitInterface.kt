package com.example.songil.page_craft

import com.example.songil.page_craft.models.RequestCarts
import com.example.songil.page_craft.models.ResponseCarts
import com.example.songil.page_craft.models.ResponseProductDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CraftRetrofitInterface {
    @GET("products/{productIdx}")
    suspend fun getProducts(@Path("productIdx") productIdx : Int) : Response<ResponseProductDetail>

    @POST("carts")
    suspend fun postCarts(@Body params : RequestCarts) : Response<ResponseCarts>
}