package com.example.songil.page_cancel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CancelViewModel : ViewModel() {
    var reasonIdx = MutableLiveData<Int>()
    var cancelButtonActivate = MutableLiveData<Boolean>(false)
    var reasonContent = ""

    fun changeIdx(idx : Int){
        reasonIdx.value = idx
        checkContent()
    }

    fun checkContent(){
        cancelButtonActivate.value = ((reasonIdx.value!! in 0 until 4) || (reasonContent.length in 1 until 301))
    }
}