package com.songil.songil.page_mypage_modify_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.utils.toPlainRequestBody
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ModifyProfileViewModel : BaseViewModel() {

    private val repository = ModifyProfileRepository()
    private var userNickname : String = ""

    private val _nicknameCheckResult = MutableLiveData<Int>()
    val nicknameCheckResult : LiveData<Int> get() = _nicknameCheckResult

    private val _modifyProfileResult = MutableLiveData<Int>()
    val modifyProfileResult : LiveData<Int> get() = _modifyProfileResult

    val btnActivate = MutableLiveData(false)

    var prevNickname = ""

    fun blockBtnActivate() {
        btnActivate.value = false
    }

    fun activateBtn(){
        btnActivate.value = true
    }

    fun tryCheckNicknameCheck(inputNickname : String){
        viewModelScope.launch(exceptionHandler) {
            if(inputNickname == prevNickname){
                _nicknameCheckResult.postValue(200)
            } else {
                val result = repository.checkNickname(inputNickname)
                if (result.body()?.code == 200){
                    userNickname = inputNickname
                }
                _nicknameCheckResult.postValue(result.body()?.code ?: -1)
            }
        }
    }

    fun tryModifyProfile(file : File?){
        viewModelScope.launch(exceptionHandler) {
            var requestImage : MultipartBody.Part? = null
            if (file !=  null){
                val requestBody = file.asRequestBody("image/*".toMediaType())
                requestImage = MultipartBody.Part.createFormData("userProfile", file.name, requestBody)
            }

            val hashMap = hashMapOf<String, RequestBody>()
            if (userNickname != "")
                hashMap["userName"] = toPlainRequestBody(userNickname)

            val result = repository.modifyProfile(data = hashMap, image = requestImage)
            file?.delete()
            _modifyProfileResult.postValue(result.body()?.code ?: -1)
        }
    }
}