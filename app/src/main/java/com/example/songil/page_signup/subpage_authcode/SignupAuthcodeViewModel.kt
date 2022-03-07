package com.example.songil.page_signup.subpage_authcode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.page_signup.SignupRepository
import com.example.songil.page_signup.models.SignUpInfo
import kotlinx.coroutines.launch

class SignupAuthcodeViewModel(private val signUpInfo: SignUpInfo) : BaseViewModel() {
    private val repository = SignupRepository.getInstance()

    class SignupAuthcodeViewModelFactory(private val signUpInfo: SignUpInfo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SignupAuthcodeViewModel(signUpInfo) as T
        }
    }

    var inputAuthcode = ""
    var btnActivate = MutableLiveData(false)

    var issueAuthCodeResult = MutableLiveData<Int>()
    var checkAuthCodeResult = MutableLiveData<Int>()

    var timerCount = MutableLiveData<Int>()

    fun checkAuthButtonStatus(){
        btnActivate.value = inputAuthcode.matches(Regex("[0-9]{6}\$")) && timerCount.value != null && timerCount.value!! > 0
    }

    fun setConfirmEmail(){
        signUpInfo.confirmEmail()
    }

    fun tryIssueAuthCodeAgain() {
        viewModelScope.launch(exceptionHandler) {
            val result = repository.issueAuthCode(signUpInfo.requestEmail)
            issueAuthCodeResult.postValue(result.body()!!.code)
        }
    }

    fun tryCheckAuthCode(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.checkAuthCode(signUpInfo.requestEmail, inputAuthcode)
            checkAuthCodeResult.postValue(result.body()!!.code)
        }
    }
}