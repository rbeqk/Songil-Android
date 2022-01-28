package com.example.songil.page_signup.subpage_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.songil.config.BaseViewModel
import com.example.songil.page_signup.models.SignUpInfo

class SignupPasswordViewModel(private val signUpInfo: SignUpInfo) : BaseViewModel() {

    var btnActivate = MutableLiveData(false)

    var password = ""

    class SignupPasswordViewModelFactory(private val signUpInfo: SignUpInfo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SignupPasswordViewModel(signUpInfo) as T
        }
    }

    fun setPassword(){
        signUpInfo.password = password
    }

    fun checkPasswordForm(){
        btnActivate.value = password.matches(Regex("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[\\-!\"#$%&\'()*+,./;:?@\\[\\]^\\\\`{|}~]).{8,16}\$")) && !password.contains(" ")
    }
}