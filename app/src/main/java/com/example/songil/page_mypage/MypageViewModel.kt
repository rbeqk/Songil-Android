package com.example.songil.page_mypage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.config.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MypageViewModel : ViewModel(){
    private val repository = MypageRepository()
    var logoutSuccess = MutableLiveData<Boolean>()

    fun tryLogout(){
/*        val edit = GlobalApplication.globalSharedPreferences.edit()
        edit.remove(GlobalApplication.X_ACCESS_TOKEN)
        edit.remove(GlobalApplication.USER_IDX)
        edit.apply()*/
        //getGuestUser()
        val edit = GlobalApplication.globalSharedPreferences.edit()
        edit.remove(GlobalApplication.X_ACCESS_TOKEN).apply()
    }

    private fun getGuestUser(){
        var jwt = ""
        var userIdx = -1

        CoroutineScope(Dispatchers.IO).launch {
            repository.getJwt().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1000){
                        jwt = response.body()!!.result.jwt
                        GlobalApplication.globalSharedPreferences.edit().putString(GlobalApplication.X_ACCESS_TOKEN, jwt).apply()
                    }
                }
            }

            repository.getUserIdx().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1001){
                        userIdx = response.body()!!.result.userIdx
                    }
                }
            }

            if (jwt != "" && userIdx != -1){
                GlobalApplication.globalSharedPreferences.edit().putInt(GlobalApplication.USER_IDX, userIdx).apply()
                logoutSuccess.postValue(true)
            } else {
                logoutSuccess.postValue(false)
            }

        }
    }
}