package com.songil.songil.page_login.subpage_password

import androidx.lifecycle.*
import com.songil.songil.config.BaseViewModel
import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_login.LoginRepository
import com.songil.songil.page_login.models.LoginInfo
import kotlinx.coroutines.launch

class LoginPasswordViewModel(private val loginInfo: LoginInfo) : BaseViewModel() {

    private val repository = LoginRepository()
    var btnActivate = MutableLiveData(false)
    var password = ""

    var loginResult = MutableLiveData<Int>()

    private val _userTypeResultCode = MutableLiveData<Int>()
    val userTypeResultCode : LiveData<Int> get() = _userTypeResultCode

    class LoginPasswordViewModelFactory(private val loginInfo: LoginInfo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return LoginPasswordViewModel(loginInfo) as T
        }
    }

    fun setPassword(){
        loginInfo.password = password
    }

    fun tryLogin(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.doLogin(loginInfo)
            if (result.body()?.code == 200){
                val edit = GlobalApplication.globalSharedPreferences.edit()
                edit.putString(GlobalApplication.X_ACCESS_TOKEN, result.body()!!.result.jwt).apply()
            }
            loginResult.postValue(result.body()?.code ?: -1)
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

    fun changeBtnState() {
        btnActivate.value = password.replace(" ", "").isNotEmpty()
    }
}