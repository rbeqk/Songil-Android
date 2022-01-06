package com.example.songil.page_artistmanage.subpage_answer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArtistManageAnswerViewModel : ViewModel() {
    var btnActivate = MutableLiveData(false)
    var inquiryContent = ""
    var answerContent = ""

    fun tempGetInquiry(){
        inquiryContent = "임시 문의 내용입니다."
    }

    fun checkActivate(){
        btnActivate.value = (answerContent != "")
    }
}