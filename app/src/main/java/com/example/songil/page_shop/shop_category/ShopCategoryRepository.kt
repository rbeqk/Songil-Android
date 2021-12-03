package com.example.songil.page_shop.shop_category

import com.example.songil.config.GlobalApplication
import com.example.songil.page_shop.shop_category.models.CraftDetail

class ShopCategoryRepository {
    private val shopCategoryRetrofitInter = GlobalApplication.sRetrofit.create(ShopCategoryRetrofitInterface::class.java)

    suspend fun getProductAll(category : String, filter : String = "popular", page : Int = 1) = shopCategoryRetrofitInter.postAllProduct(category, page, filter)

    fun tempPagingDetailData(category : String = GlobalApplication.categoryList[0].category, filter : String = "popular", page : Int = 1) : ArrayList<CraftDetail> {
        // 일단 20개
        return if (page == 0){
            arrayListOf()
        }
        else {
            val temp = ArrayList<CraftDetail>()
            for (i in 0 until 20){
                val tempIdx = page * 20 + i
                temp.add(CraftDetail(i, "${category}_${filter}_${tempIdx}", "", 124, 12, 20000, "프로브","Y", 1))
            }
            temp
        }
    }

}