package com.example.songil.page_myfavorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.Craft1
import com.example.songil.page_shop.shop_category.models.CraftDetail

class MyfavoriteViewModel : ViewModel() {
    var craftList = MutableLiveData<ArrayList<Craft1>>()

    fun getCraftData(){
        val temp = ArrayList<Craft1>()
        temp.add(Craft1(1, name = "작품1", mainImageUrl = "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", artistIdx =  1, price = 5000, artistName = "자까", isNew = "N", isSoldOut = "Y", totalLikeCnt = 3, totalCommentCnt = 12, isLike = "Y"))
        temp.add(Craft1(1, name = "작품2", mainImageUrl = "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", artistIdx =  1, price = 5000, artistName = "자까", isNew = "N", isSoldOut = "Y", totalLikeCnt = 3, totalCommentCnt = 12, isLike = "Y"))
        temp.add(Craft1(1, name = "작품3", mainImageUrl = "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", artistIdx =  1, price = 5000, artistName = "자까", isNew = "N", isSoldOut = "Y", totalLikeCnt = 3, totalCommentCnt = 12, isLike = "Y"))
        temp.add(Craft1(1, name = "작품4", mainImageUrl = "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", artistIdx =  1, price = 5000, artistName = "자까", isNew = "N", isSoldOut = "Y", totalLikeCnt = 3, totalCommentCnt = 12, isLike = "Y"))
        temp.add(Craft1(1, name = "작품5", mainImageUrl = "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", artistIdx =  1, price = 5000, artistName = "자까", isNew = "N", isSoldOut = "Y", totalLikeCnt = 3, totalCommentCnt = 12, isLike = "Y"))
        craftList.value = temp
    }
}