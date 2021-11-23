package com.example.songil.page_login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.config.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()
    var phoneNumber = ""
    var inputAuthNumber = ""
    var btn1Activate = MutableLiveData<Boolean>()
    var btn2Activate = MutableLiveData<Boolean>()

    var apiResultMessage = ""
    var loginResultCode = MutableLiveData<Int>()
    var authResultCode = MutableLiveData<Int>()

    init {
        btn1Activate.value = false
        btn2Activate.value = false
    }

    fun checkPhoneNumberForm(){
        btn1Activate.value = phoneNumber.matches(Regex("^[0-9]{3}[0-9]{4}[0-9]{4}\$"))
    }

    fun checkAuthNumberForm(){
        btn2Activate.value = inputAuthNumber.matches(Regex("[0-9]{6}\$"))
    }

    fun checkAuthNumberValue() {
        tryLogin()
    }

    private fun tryLogin(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.tryLogin(phoneNumber, inputAuthNumber).let { response ->
                if (response.isSuccessful){
                    apiResultMessage = response.body()!!.message!!
                    if (response.body()!!.code == 200){
                        val jwt = response.body()!!.result.jwt
                        val edit = GlobalApplication.globalSharedPreferences.edit()
                        edit.putString(GlobalApplication.X_ACCESS_TOKEN, jwt).apply()
                    }
                    loginResultCode.postValue(response.body()!!.code)
                }
            }
        }
    }

    fun trySetAuthNumber(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.setAuthNumber(phoneNumber).let { response ->
                if (response.isSuccessful){
                    apiResultMessage = response.body()!!.message!!
                    authResultCode.postValue(response.body()!!.code)
                }
            }
        }
    }
}