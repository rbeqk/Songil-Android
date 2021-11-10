package com.example.songil.page_login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.config.GlobalApplication
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

    private fun tryLogin(){
        CoroutineScope(Dispatchers.IO).launch {
            var jwt = ""
            repository.getLogin(phoneNumber).let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.isSuccess){
                        //Log.d("login", response.body()!!.result.jwt)
                        jwt = response.body()!!.result.jwt
                        val edit = GlobalApplication.globalSharedPreferences.edit()
                        edit.putString(GlobalApplication.X_ACCESS_TOKEN, jwt)
                        edit.apply()
                        //fragmentIdx.postValue(2)
                    } else {
                        Log.d("login", response.body()!!.message!!)
                    }
                } else {
                    Log.d("login", response.body()!!.message!!)
                }
            }
            if (jwt != ""){
                repository.getUserIdx().let { response ->
                    if (response.isSuccessful){
                        if (response.body()!!.isSuccess){
                            Log.d("userIdx", response.body()!!.result.userIdx.toString())
                            val edit = GlobalApplication.globalSharedPreferences.edit()
                            edit.putInt(GlobalApplication.USER_IDX, response.body()!!.result.userIdx)
                            edit.apply()
                            fragmentIdx.postValue(2)
                        } else {
                            // 로그인 userIdx 를 얻어오는데 실패하면 그냥 jwt 삭제
                            Log.d("userIdx", response.body()!!.message!!)
                            val edit = GlobalApplication.globalSharedPreferences.edit()
                            edit.remove(GlobalApplication.X_ACCESS_TOKEN)
                            edit.apply()
                        }
                    } else {
                        Log.d("userIdx", response.body()!!.message!!)
                    }
                }
            }
        }
    }
}