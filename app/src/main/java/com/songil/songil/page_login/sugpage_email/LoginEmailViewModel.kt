package com.songil.songil.page_login.sugpage_email

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.songil.songil.config.BaseViewModel
import com.songil.songil.page_login.models.LoginInfo

class LoginEmailViewModel(private val loginInfo: LoginInfo) : BaseViewModel() {

    var email = ""
    var btnActivate = MutableLiveData(false)

    class LoginEmailViewModelFactory(private val loginInfo: LoginInfo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return LoginEmailViewModel(loginInfo) as T
        }
    }

    fun setEmail(){
        loginInfo.email = email.trim()
    }

    fun changeBtnState(){
        btnActivate.value = email.replace(" ", "").isNotEmpty()
    }
}