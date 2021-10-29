package com.example.songil.page_login

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var phoneNumber = MutableLiveData<String>()
    var btn1Activate = MutableLiveData<Boolean>()
    var btn2Activate = MutableLiveData<Boolean>()
    var inputAuthNumber = MutableLiveData<String>()
    var authNumber = ""


    init {
        phoneNumber.value = ""
        btn1Activate.value = false
        btn2Activate.value = false
        inputAuthNumber.value = ""
    }

    // 예, 저도 viewModel 에는 UI 관련 코드 넣으면 안되는거 압니다...
    // 근데... 방법이 안떠올라요....ㅠ
    fun sendPhoneNumber(successFunc : () -> Unit){
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            authNumber = "685746"
            successFunc()
        }, 1000)
    }

    fun checkPhoneNumberForm(){
        btn1Activate.value = phoneNumber.value!!.matches(Regex("^[0-9]{3}[0-9]{4}[0-9]{4}\$"))
    }

    fun checkAuthNumberForm(){
        btn2Activate.value = inputAuthNumber.value!!.matches(Regex("[0-9]{6}\$"))
    }

    fun checkAuthNumberValue() {
        if (inputAuthNumber.value == authNumber) {
            Log.d("login", "success")
        }
    }
}