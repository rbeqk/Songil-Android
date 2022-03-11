package com.songil.songil.page_signup.subpage_email

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.page_signup.SignupRepository
import com.songil.songil.page_signup.models.SignUpInfo
import kotlinx.coroutines.launch

class SignupEmailViewModel(private val signUpInfo: SignUpInfo) : BaseViewModel() {
    private val repository = SignupRepository.getInstance()

    class SignupEmailViewModelFactory(private val signUpInfo: SignUpInfo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SignupEmailViewModel(signUpInfo) as T
        }
    }

    var emailAddress = ""
    var btnActivate = MutableLiveData(false)

    var issueAuthCodeResult = MutableLiveData<Int>()

    fun tryIssueAuthCode() {
        viewModelScope.launch(exceptionHandler) {
            val result = repository.issueAuthCode(emailAddress)
            issueAuthCodeResult.postValue(result.body()!!.code)
        }
    }

    fun setRequestEmail() {
        signUpInfo.requestEmail = emailAddress
    }

    fun checkEmailForm(){
        btnActivate.value = emailAddress.matches(Regex("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+.[A-Za-z]{2,}\$")) && !emailAddress.contains(" ")
    }
}