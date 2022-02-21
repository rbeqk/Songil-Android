package com.example.songil.page_mypage


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.config.GlobalApplication
import com.example.songil.data.SongilUserInfo
import kotlinx.coroutines.launch

class MypageViewModel : BaseViewModel(){
    private val repository = MypageRepository()
    var isLogin = MutableLiveData<Boolean>()

    private val _userInfo = MutableLiveData(SongilUserInfo())
    val userInfo : LiveData<SongilUserInfo> get() = _userInfo

    fun checkLogin(){
        isLogin.value = GlobalApplication.globalSharedPreferences.contains(GlobalApplication.X_ACCESS_TOKEN)
    }

    // after logout
    fun clearUserInfo(defaultUserName : String = ""){
        _userInfo.value = SongilUserInfo(userName = defaultUserName)
    }

    fun tryLogout(){
        isLogin.value = false
    }

    fun tryGetMyInfo(){
        viewModelScope.launch(exceptionHandler){
            val result = repository.getMyInfo()
            if (result.body()?.code == 200){
                _userInfo.postValue(result.body()!!.result)

            }
        }
    }
}