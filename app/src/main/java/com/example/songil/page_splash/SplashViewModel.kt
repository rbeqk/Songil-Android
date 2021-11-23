package com.example.songil.page_splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.config.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    var autoLoginResultCode = MutableLiveData<Int>()
    private val repository = SplashRepository()

    fun tryAutoLogin(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAutoLogin().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code != 200){
                        val edit = GlobalApplication.globalSharedPreferences.edit()
                        edit.remove(GlobalApplication.X_ACCESS_TOKEN).apply()
                    }
                    autoLoginResultCode.postValue(response.body()!!.code)
                } else {
                    autoLoginResultCode.postValue(-1)
                }
            }
        }
    }
}