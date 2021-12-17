package com.example.songil.page_report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportViewModel : ViewModel() {
    var reasonIdx = MutableLiveData<Int>()
    val reportButtonActivate = MutableLiveData(false)
    var reasonContent = ""

    fun changeIdx(idx : Int){
        reasonIdx.value = idx
        checkContent()
    }

    fun checkContent(){
        reportButtonActivate.value = ((reasonIdx.value!! in 0 until 6) || reasonContent.length in 1 until 301)
    }
}