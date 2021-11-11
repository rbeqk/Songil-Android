package com.example.songil.page_myfavorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.page_shop.shop_category.models.CraftDetail

class MyfavoriteViewModel : ViewModel() {
    var craftList = MutableLiveData<ArrayList<CraftDetail>>()

    fun getCraftData(){
        val temp = ArrayList<CraftDetail>()
        temp.add(CraftDetail(1, "test", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", 12, 1, 5000, "자까", "NEW", 1))
        temp.add(CraftDetail(2, "이름", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", 11, 2, 15000, "자까", "NEW", 0))
        temp.add(CraftDetail(3, "무명", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", 0, 1, 5000, "자까", "NOT NEW", 0))
        temp.add(CraftDetail(4, "공간", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", 3, 3, 25000, "자까", "NEW", 1))
        temp.add(CraftDetail(5, "휴식", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", 0, 1, 5000, "자까", "NOT NEW", 0))
        temp.add(CraftDetail(6, "끈기", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", 3, 3, 25000, "자까", "NEW", 1))
        craftList.value = temp
    }
}