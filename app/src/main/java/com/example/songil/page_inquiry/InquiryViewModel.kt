package com.example.songil.page_inquiry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InquiryViewModel : ViewModel() {
    var reviewContent = ""
    var sizeText = MutableLiveData("0/300")
    var buttonActivate = MutableLiveData(false)


    fun changeTextLength()  {
        sizeText.value =  "${reviewContent.length}/300"
        buttonActivate.value = reviewContent.length in 1..300
    }

}