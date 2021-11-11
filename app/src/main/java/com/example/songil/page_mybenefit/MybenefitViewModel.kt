package com.example.songil.page_mybenefit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.Benefit

class MybenefitViewModel : ViewModel() {
    var benefitDatas = MutableLiveData<ArrayList<Benefit>>()

    fun getBenefitData(){
        val temp = ArrayList<Benefit>()
        temp.add(Benefit(1, "10% 할인", "손길 오픈 기념 단독 베네핏", "2021.12.12까지", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg"))
        temp.add(Benefit(1, "15% 할인", "손길 오픈 기념 단독 베네핏", "2021.12.12까지", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg"))
        temp.add(Benefit(1, "10% 할인", "손길 오픈 기념 단독 베네핏", "2021.12.12까지", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg"))
        temp.add(Benefit(1, "20% 할인", "신규회원 전용 베네핏", "2022.01.24까지", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg"))
        temp.add(Benefit(1, "10% 할인", "신규회원 전용 베네핏", "2022.01.24까지", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg"))
        benefitDatas.value = temp
    }
}