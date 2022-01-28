package com.example.songil.page_login.subpage_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.config.GlobalApplication
import com.example.songil.page_login.LoginRepository
import com.example.songil.page_login.models.LoginInfo
import kotlinx.coroutines.launch

class LoginPasswordViewModel(private val loginInfo: LoginInfo) : BaseViewModel() {

    private val repository = LoginRepository()
    var btnActivate = MutableLiveData(false)
    var password = ""

    var loginResult = MutableLiveData<Int>()

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

    fun changeBtnState() {
        btnActivate.value = password.replace(" ", "").isNotEmpty()
    }
}