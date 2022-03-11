package com.songil.songil.page_signup.subpage_nickname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_signup.SignupRepository
import com.songil.songil.page_signup.models.SignUpInfo
import kotlinx.coroutines.launch

class SignupNicknameViewModel(private val signUpInfo: SignUpInfo) : BaseViewModel() {

    private val repository = SignupRepository.getInstance()

    var btnActivate = MutableLiveData(false)

    var nicknameCheckResult = MutableLiveData<Int>()
    var signupResult = MutableLiveData<Boolean>()

    class SignupNicknameViewModelFactory(private val signUpInfo: SignUpInfo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SignupNicknameViewModel(signUpInfo) as T
        }
    }

    private fun checkNicknameForm(nickname : String) : Boolean {
        return nickname.matches(Regex("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]{1,10}\$")) && !nickname.contains(" ")
    }

    fun tryNicknameDupCheck(nickname : String){
        if (checkNicknameForm(nickname)){
            viewModelScope.launch(exceptionHandler) {
                val result = repository.checkNickname(nickname)
                if (result.body()?.code == 200){
                    signUpInfo.nickname = nickname
                }
                nicknameCheckResult.postValue(result.body()?.code ?: -1)
            }
        } else {
            if (nickname.length > 10){
                nicknameCheckResult.postValue(2367)
            }
            else {
                nicknameCheckResult.postValue(-2)
            }
        }
    }

    fun trySignUp(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.signup(signUpInfo.getRequestForm())
            if (result.body()?.code == 200){
                val edit = GlobalApplication.globalSharedPreferences.edit()
                edit.putString(GlobalApplication.X_ACCESS_TOKEN, result.body()!!.result.jwt).apply()
            }
            signupResult.postValue(result.body()?.code == 200)
        }
    }

}