package com.example.songil.page_shop.shop_category

import com.example.songil.page_shop.shop_category.models.ResponseProductAll
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopCategoryRetrofitInterface {
    @GET("products")
    suspend fun postAllProduct(@Query("category") category : String, @Query("page") page : Int, @Query("filter") filter : String) : Response<ResponseProductAll>
}