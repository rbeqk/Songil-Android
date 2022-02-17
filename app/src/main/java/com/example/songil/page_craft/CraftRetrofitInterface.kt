package com.example.songil.page_craft

import com.example.songil.config.BaseResponse
import com.example.songil.page_basket.models.Amount
import com.example.songil.page_craft.models.ResponseCraftDetail
import com.example.songil.page_craft.models.ResponseCraftLike
import retrofit2.Response
import retrofit2.http.*

interface CraftRetrofitInterface {
    @GET("shop/crafts/{craftIdx}")
    suspend fun getProducts(@Path("craftIdx") craftIdx : Int) : Response<ResponseCraftDetail>

    @PATCH("shop/crafts/{craftIdx}/like")
    suspend fun patchCraftLike(@Path("craftIdx") craftIdx : Int) : Response<ResponseCraftLike>

    // use data in page_basket's Amount
    @POST("cart/crafts/{craftIdx}")
    suspend fun postCraftItem(@Path("craftIdx") craftIdx : Int, @Body params : Amount) : Response<BaseResponse>
}