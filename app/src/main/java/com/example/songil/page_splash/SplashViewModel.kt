package com.example.songil.page_splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.config.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    var authLoginResultCode = MutableLiveData<Int>()
    var authJwtResultCode = MutableLiveData<Int>()
    private val repository = SplashRepository()

    fun requestAuthLogin(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.postAuthLogin().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1000) {
                        val edit = GlobalApplication.globalSharedPreferences.edit()
                        edit.putString(GlobalApplication.X_ACCESS_TOKEN, response.body()!!.result.jwt).apply()
                    }
                    authLoginResultCode.postValue(response.body()!!.code)
                }
            }
        }
    }

    fun requestAuthJwt(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAuthJwt().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1001){
                        val edit = GlobalApplication.globalSharedPreferences.edit()
                        edit.putInt(GlobalApplication.USER_IDX, response.body()!!.result.userIdx).apply()
                    }
                    authJwtResultCode.postValue(response.body()!!.code)
                }
            }
        }
    }
}