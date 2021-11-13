package com.example.songil.page_mycomment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MycommentViewModel : ViewModel() {
    var btnWriteable = MutableLiveData<Boolean>(true)
    var btnWritten = MutableLiveData<Boolean>(false)
    var currentFragmentIdx = MutableLiveData<Int>()

    fun changeFragment(fragmentIdx : Int){
        currentFragmentIdx.value = fragmentIdx
        when (fragmentIdx){
            0 -> {
                btnWriteable.value = true
                btnWritten.value = false
            }
            else -> {
                btnWriteable.value = false
                btnWritten.value = true
            }
        }
    }
}
