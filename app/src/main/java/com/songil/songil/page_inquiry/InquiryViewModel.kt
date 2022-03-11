package com.songil.songil.page_inquiry

import androidx.lifecycle.*
import com.songil.songil.config.BaseViewModel
import com.songil.songil.config.InquiryTarget
import kotlinx.coroutines.launch

class InquiryViewModel(private val targetIdx : Int, private val targetType : InquiryTarget) : BaseViewModel() {
    private val repository = InquiryRepository()

    var reviewContent = ""
    var sizeText = MutableLiveData("0/300")
    var buttonActivate = MutableLiveData(false)

    private val _inquiryResult = MutableLiveData<Boolean>()
    val inquiryResult : LiveData<Boolean> get() = _inquiryResult

    class InquiryViewModelFactory(private val targetIdx : Int, private val targetType : InquiryTarget) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return InquiryViewModel(targetIdx = targetIdx, targetType = targetType) as T
        }
    }

    fun changeTextCntTextView(){
        sizeText.value =  "${reviewContent.length}/300"
    }

    fun checkTextSizeInRange()  {
        buttonActivate.value = reviewContent.length in 1..300
    }

    fun trySendInquiry(){
        viewModelScope.launch(exceptionHandler) {
            when (targetType){
                InquiryTarget.CRAFT -> {
                    val result = repository.sendCraftInquiry(craftIdx = targetIdx, content = reviewContent)
                    _inquiryResult.postValue(result.body()?.code == 200)
                }
                InquiryTarget.ORDER -> {
                    val result = repository.sendOrderInquiry(orderDetailIdx = targetIdx, content = reviewContent)
                    _inquiryResult.postValue(result.body()?.code == 200)
                }
            }
        }
    }

}