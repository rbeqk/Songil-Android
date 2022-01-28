package com.example.songil.page_signup.subpage_password_confirm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.songil.config.BaseViewModel
import com.example.songil.page_signup.models.SignUpInfo

class SignupPasswordConfirmViewModel(private val signUpInfo: SignUpInfo) : BaseViewModel() {

    var btnActivate = MutableLiveData(false)
    var inputPassword = ""

    class SignupPasswordConfirmViewModelFactory(private val signUpInfo: SignUpInfo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SignupPasswordConfirmViewModel(signUpInfo) as T
        }
    }

    fun confirmPassword() : Boolean {
        return signUpInfo.checkPassword(inputPassword)
    }

    fun checkPasswordForm(){
        btnActivate.value = inputPassword.matches(Regex("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[\\-!\"#$%&\'()*+,./;:?@\\[\\]^\\\\`{|}~]).{8,16}\$")) && !inputPassword.contains(" ")
    }
}