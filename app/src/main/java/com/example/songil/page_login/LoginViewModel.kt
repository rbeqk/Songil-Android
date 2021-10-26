package com.example.songil.page_login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var fragmentIdx = MutableLiveData<Int>()
    var phoneNumber = MutableLiveData<String>()
    var btnActivate = MutableLiveData<Boolean>()

    init {
        fragmentIdx.value = 0
        phoneNumber.value = ""
        btnActivate.value = false
    }

    fun showPhoneNumber(){
        Log.d("phoneNumber", phoneNumber.value!!.toString())
    }

    fun checkPhoneNumber(){
        btnActivate.value = phoneNumber.value!!.matches(Regex("^[0-9]{3}[0-9]{4}[0-9]{4}\$"))
    }
}