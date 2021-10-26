package com.example.songil.page_login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var fragmentIdx = MutableLiveData<Int>()
    var phoneNumber = MutableLiveData<String>()
    var btn1Activate = MutableLiveData<Boolean>()

    init {
        fragmentIdx.value = 0
        phoneNumber.value = ""
        btn1Activate.value = false
    }

    fun showPhoneNumber(){
        Log.d("phoneNumber", phoneNumber.value!!.toString())
    }

    fun checkPhoneNumber(){
        btn1Activate.value = phoneNumber.value!!.matches(Regex("^[0-9]{3}[0-9]{4}[0-9]{4}\$"))
    }
}