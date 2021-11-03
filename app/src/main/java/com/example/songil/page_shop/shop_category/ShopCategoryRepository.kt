package com.example.songil.page_shop.shop_category

import com.example.songil.config.GlobalApplication

class ShopCategoryRepository {
    private val shopCategoryRetrofitInter = GlobalApplication.sRetrofit.create(ShopCategoryRetrofitInterface::class.java)

    suspend fun getProductAll(category : String, page : Int = 1) = shopCategoryRetrofitInter.postAllProduct(category, page)

}