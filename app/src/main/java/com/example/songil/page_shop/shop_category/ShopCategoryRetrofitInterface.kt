package com.example.songil.page_shop.shop_category

import com.example.songil.page_shop.shop_category.models.ResponseCategoryCraft
import com.example.songil.page_shop.shop_category.models.ResponsePopularCraft
import com.example.songil.page_shop.shop_category.models.ResponseStartPageIdx
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ShopCategoryRetrofitInterface {
    @GET("shop/crafts")
    suspend fun getCategoryCraft(@Query("categoryIdx") categoryIdx : Int, @Query("page") page : Int, @Query("sort") sort : String) : Response<ResponseCategoryCraft>

    @GET("shop/crafts/popular")
    suspend fun getPopularCraft(@Query("categoryIdx") categoryIdx : Int) : Response<ResponsePopularCraft>

    @GET("shop/crafts/page")
    suspend fun getStartPageIdx(@Query("categoryIdx") categoryIdx : Int) : Response<ResponseStartPageIdx>
}