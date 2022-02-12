package com.example.songil.page_shop.shop_category

import com.example.songil.config.GlobalApplication
import com.example.songil.data.Craft1
import com.example.songil.page_shop.shop_category.models.ResponseCategoryCraft
import com.example.songil.page_shop.shop_category.models.ResponsePopularCraft
import com.example.songil.page_shop.shop_category.models.ResponseStartPageIdx
import retrofit2.Response

class ShopCategoryRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ShopCategoryRetrofitInterface::class.java)

    suspend fun tryGetProductAll(category : Int, filter : String = "popular", page : Int = 1) : Response<ResponseCategoryCraft> {
        val result = retrofitInterface.getCategoryCraft(categoryIdx = category, page = page, sort = filter)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun getStartIdx(category : Int) : Response<ResponseStartPageIdx> {
        val result = retrofitInterface.getStartPageIdx(categoryIdx = category)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun getPopularCraft(categoryIdx : Int) : Response<ResponsePopularCraft> {
        val result = retrofitInterface.getPopularCraft(categoryIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

}