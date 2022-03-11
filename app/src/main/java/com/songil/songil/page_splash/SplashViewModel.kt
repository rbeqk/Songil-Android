package com.songil.songil.page_splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.config.GlobalApplication
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {
    var autoLoginResultCode = MutableLiveData<Int>()
    private val repository = SplashRepository()

    private val _userTypeResultCode = MutableLiveData<Int>()
    val userTypeResultCode : LiveData<Int> get() = _userTypeResultCode

    fun tryAutoLogin(){
         viewModelScope.launch(exceptionHandler) {
            repository.getAutoLogin().let { response ->
                if (response.isSuccessful){
                    if (response.body()?.code != 200){
                        logout()
                    } else {
                        val newJwt = response.body()!!.result.jwt
                        val edit = GlobalApplication.globalSharedPreferences.edit()
                        edit.putString(GlobalApplication.X_ACCESS_TOKEN, newJwt).apply()
                    }
                    autoLoginResultCode.postValue(response.body()!!.code)
                } else {
                    logout()
                    autoLoginResultCode.postValue(-1)
                }
            }
        }
    }

    fun tryGetUserType(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getUserType()
            if (result.code == 200){
                val edit = GlobalApplication.globalSharedPreferences.edit()
                edit.putBoolean(GlobalApplication.IS_ARTIST, result.result.type == 2).apply()
            } else {
                val edit = GlobalApplication.globalSharedPreferences.edit()
                edit.putBoolean(GlobalApplication.IS_ARTIST, false).apply()
            }
            _userTypeResultCode.postValue(result.code)
        }
    }

    private fun logout(){
        val edit = GlobalApplication.globalSharedPreferences.edit()
        edit.remove(GlobalApplication.X_ACCESS_TOKEN).apply()
        edit.remove(GlobalApplication.IS_ARTIST).apply()
    }
}