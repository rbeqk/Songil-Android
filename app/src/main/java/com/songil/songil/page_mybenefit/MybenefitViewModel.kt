package com.songil.songil.page_mybenefit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.data.Benefit
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
}