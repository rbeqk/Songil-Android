package com.example.songil.page_craft

import com.example.songil.page_craft.models.RequestCarts
import com.example.songil.page_craft.models.ResponseCarts
import com.example.songil.page_craft.models.ResponseCraftDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CraftRetrofitInterface {
    @GET("shop/crafts/{craftIdx}")
    suspend fun getProducts(@Path("craftIdx") craftIdx : Int) : Response<ResponseCraftDetail>

    @POST("carts")
    suspend fun postCarts(@Body params : RequestCarts) : Response<ResponseCarts>
}