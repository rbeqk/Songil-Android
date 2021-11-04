package com.example.songil.page_login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()
    var phoneNumber = ""
    var authNumber = MutableLiveData<String>()
    var btn1Activate = MutableLiveData<Boolean>()
    var btn2Activate = MutableLiveData<Boolean>()
    var inputAuthNumber = ""

    var fragmentIdx = MutableLiveData<Int>(0)

    init {
        btn1Activate.value = false
        btn2Activate.value = false
    }

    fun checkPhoneNumberForm(){
        btn1Activate.value = phoneNumber.matches(Regex("^[0-9]{3}[0-9]{4}[0-9]{4}\$"))
    }

    fun checkAuthNumberForm(){
        btn2Activate.value = inputAuthNumber.matches(Regex("[0-9]{4}\$"))
    }

    fun checkAuthNumberValue() {
        if (inputAuthNumber == authNumber.value) {
            tryLogin()
        } else {
            Log.d("login", "인증번호가 일치하지 않습니다.")
        }
    }

    fun setFragmentIdx(idx : Int){
        fragmentIdx.value = idx
    }

    // network!!
    fun tryGetAuthNumber(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAuthNumber(phoneNumber).let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.isSuccess){
                        authNumber.postValue(response.body()!!.result.authNumber)
                        fragmentIdx.postValue(1)
                    } else {
                        Log.d("get auth number", response.body()!!.message!!)
                    }
                } else {
                    Log.d("get auth number", response.body()!!.message!!)
                }
            }
        }
    }

    // 일단 테스트하느라 sp 에는 저장 안했다. 이후에 저장할 것!!!!
    fun tryLogin(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getLogin(phoneNumber).let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.isSuccess){
                        Log.d("login", response.body()!!.result.jwt)
                        fragmentIdx.postValue(2)
                    } else {
                        Log.d("login", response.body()!!.message!!)
                    }
                } else {
                    Log.d("login", response.body()!!.message!!)
                }
            }
        }
    }
}