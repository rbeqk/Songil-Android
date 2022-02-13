package com.example.songil.page_craft

import com.example.songil.page_craft.models.RequestCarts
import com.example.songil.page_craft.models.ResponseCarts
import com.example.songil.page_craft.models.ResponseCraftDetail
import com.example.songil.page_craft.models.ResponseCraftLike
import retrofit2.Response
import retrofit2.http.*

interface CraftRetrofitInterface {
    @GET("shop/crafts/{craftIdx}")
    suspend fun getProducts(@Path("craftIdx") craftIdx : Int) : Response<ResponseCraftDetail>

    @POST("carts")
    suspend fun postCarts(@Body params : RequestCarts) : Response<ResponseCarts>

    @PATCH("shop/crafts/{craftIdx}/like")
    suspend fun patchCraftLike(@Path("craftIdx") craftIdx : Int) : Response<ResponseCraftLike>
}