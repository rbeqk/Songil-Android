package com.example.songil.page_splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.config.GlobalApplication
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {
    var autoLoginResultCode = MutableLiveData<Int>()
    private val repository = SplashRepository()

    fun tryAutoLogin(){
         viewModelScope.launch(exceptionHandler) {
            repository.getAutoLogin().let { response ->
                if (response.isSuccessful){
                    if (response.body()?.code != 200){
                        val edit = GlobalApplication.globalSharedPreferences.edit()
                        edit.remove(GlobalApplication.X_ACCESS_TOKEN).apply()
                    } else {
                        val newJwt = response.body()!!.result.jwt
                        val edit = GlobalApplication.globalSharedPreferences.edit()
                        edit.putString(GlobalApplication.X_ACCESS_TOKEN, newJwt).apply()
                    }
                    autoLoginResultCode.postValue(response.body()!!.code)
                } else {
                    autoLoginResultCode.postValue(-1)
                }
            }
        }
    }
}