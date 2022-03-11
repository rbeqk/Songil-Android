package com.songil.songil.page_craft

import com.songil.songil.config.BaseResponse
import com.songil.songil.page_basket.models.Amount
import com.songil.songil.page_craft.models.ResponseCraftDetail
import com.songil.songil.page_craft.models.ResponseCraftLike
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