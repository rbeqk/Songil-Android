package com.example.songil.page_inquiry

import androidx.lifecycle.*
import com.example.songil.config.BaseViewModel
import kotlinx.coroutines.launch

class InquiryViewModel(private val craftIdx : Int) : BaseViewModel() {
    private val repository = InquiryRepository()

    var reviewContent = ""
    var sizeText = MutableLiveData("0/300")
    var buttonActivate = MutableLiveData(false)

    private val _inquiryResult = MutableLiveData<Boolean>()
    val inquiryResult : LiveData<Boolean> get() = _inquiryResult

    class InquiryViewModelFactory(private val craftIdx : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return InquiryViewModel(craftIdx = craftIdx) as T
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
            val result = repository.sendInquiry(craftIdx = craftIdx, content = reviewContent)
            _inquiryResult.postValue(result.body()?.code == 200)
        }
    }

}