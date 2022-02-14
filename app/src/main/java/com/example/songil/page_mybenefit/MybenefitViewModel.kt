package com.example.songil.page_mybenefit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.data.Benefit
import kotlinx.coroutines.launch

class MybenefitViewModel : BaseViewModel() {

    private val repository = MybenefitRepository()
    var benefitDatas = MutableLiveData<ArrayList<Benefit>>()

    fun tryGetMyBenefit(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getMyBenefit()
            if (result.body()?.code == 200){
                benefitDatas.postValue(result.body()!!.result)
            }
        }
    }

    fun tempGetBenefitData(){
        val temp = ArrayList<Benefit>()
        temp.add(Benefit(1, "10% 할인", "손길 오픈 기념 단독 베네핏", "12.12", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", ""))
        temp.add(Benefit(1, "15% 할인", "손길 오픈 기념 단독 베네핏", "12.12", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", ""))
        temp.add(Benefit(1, "10% 할인", "손길 오픈 기념 단독 베네핏", "12.12", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", ""))
        temp.add(Benefit(1, "20% 할인", "신규회원 전용 베네핏", "01.24", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", ""))
        temp.add(Benefit(1, "10% 할인", "신규회원 전용 베네핏", "01.24", "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", ""))
        benefitDatas.value = temp
    }
}