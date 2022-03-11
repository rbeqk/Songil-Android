package com.songil.songil.page_order.subpage_benefit

import androidx.lifecycle.*
import com.songil.songil.config.BaseViewModel
import com.songil.songil.data.Benefit
import kotlinx.coroutines.launch

class ApplyBenefitViewModel(private val orderIdx: Int) : BaseViewModel() {

    class ApplyBenefitViewModelFactory(private val orderIdx : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ApplyBenefitViewModel(orderIdx) as T
        }

    }

    private val repository = ApplyBenefitRepository()

    private val _getBenefitResult = MutableLiveData<Int>()
    val getBenefitResult : LiveData<Int> get() = _getBenefitResult
    val benefitList = arrayListOf<Benefit>()


    fun tryGetBenefit(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getBenefit(orderIdx)
            if (result.body()?.code == 200){
                benefitList.clear()
                benefitList.addAll(result.body()!!.result)
            }
            _getBenefitResult.postValue(result.body()?.code ?: -1)
        }
    }
}