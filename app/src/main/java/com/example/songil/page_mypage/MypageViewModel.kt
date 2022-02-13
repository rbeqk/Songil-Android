package com.example.songil.page_mypage


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.config.GlobalApplication

class MypageViewModel : ViewModel(){
    private val repository = MypageRepository()
    var isLogin = MutableLiveData<Boolean>()

    fun checkLogin(){
        isLogin.value = GlobalApplication.globalSharedPreferences.contains(GlobalApplication.X_ACCESS_TOKEN)
    }

    fun tryLogout(){
        val edit = GlobalApplication.globalSharedPreferences.edit()
        edit.remove(GlobalApplication.X_ACCESS_TOKEN).apply()
        isLogin.value = false
    }


}