package com.example.songil.page_shop.shop_category

import com.example.songil.config.GlobalApplication
import com.example.songil.data.Craft1
import com.example.songil.page_shop.shop_category.models.CraftDetail

class ShopCategoryRepository {
    private val shopCategoryRetrofitInter = GlobalApplication.sRetrofit.create(ShopCategoryRetrofitInterface::class.java)

    suspend fun getProductAll(category : String, filter : String = "popular", page : Int = 1) = shopCategoryRetrofitInter.postAllProduct(category, page, filter)

    fun tempPagingDetailData(category : String = GlobalApplication.categoryList[0].category, filter : String = "popular", page : Int = 1) : ArrayList<Craft1> {
        // 일단 20개
        return if (page == 0){
            arrayListOf()
        }
        else {
            val temp = ArrayList<Craft1>()
            for (i in 0 until 10){
                val tempIdx = page * 5 + i
                temp.add(Craft1(1, name = "${category}_${filter}_${tempIdx}", mainImageUrl = "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", artistIdx =  1, price = 5000, artistName = "프로브", isNew = "N", isSoldOut = "Y", totalLikeCnt = 3, totalCommentCnt = 12, isLike = "Y"))
            }
            temp
        }
    }

}