package com.example.songil.page_signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.config.GlobalApplication
import kotlinx.coroutines.*
import kotlin.concurrent.timer

class SignupViewModel : ViewModel() {
    private val repository = SignupRepository()

    var phoneNumber = ""
    var inputAuthNumber = ""
    var inputNickName = ""
    var fragmentIdx = MutableLiveData<Int>()
    var isNext = false

    var agreementContent = ""

    // 첫 페이지에서 약관 동의 여부
    var terms1 = MutableLiveData<Boolean>(false)
    var terms2 = MutableLiveData<Boolean>(false)
    var terms3 = MutableLiveData<Boolean>(false)
    var terms4 = MutableLiveData<Boolean>(false)
    var termsAll = MutableLiveData<Boolean>(false)

    // 각 페이지에 가장 밑에 있는 버튼의 활성화 여부
    var btn1Activate = MutableLiveData<Boolean>()
    var btn2Activate = MutableLiveData<Boolean>()
    var btn3Activate = MutableLiveData<Boolean>()
    var btn4Activate = MutableLiveData<Boolean>()

    // api 통신 결과
    var apiMessage = ""
    var checkAuthResult = MutableLiveData<Int>()
    var getAuthResult = MutableLiveData<Int>()
    var signUpResult = MutableLiveData<Int>()
    var phoneDupResult = MutableLiveData<Int>()
    var nicknameDupResult = MutableLiveData<Int>()
    var loadAgreementResult = MutableLiveData<Int>()


    init {
        btn1Activate.value = false
        btn2Activate.value = false
        btn3Activate.value = false
        btn4Activate.value = false
        fragmentIdx.value = 0
    }

    fun setFragmentIdx(number : Int){
        isNext = (number > 0) // 다음으로 가는지, 이전으로 가는지에 따라 애니메이션 효과를 다르게 적용하기 위함
        fragmentIdx.value = fragmentIdx.value?.plus(number)
        Log.d("fragmentIdx", fragmentIdx.value!!.toString())
    }

    private fun setFragmentIdxIO(number : Int){
        isNext = (number > 0)
        fragmentIdx.postValue(fragmentIdx.value?.plus(number))
    }


    // fragment1 (약관 페이지) ---------------------------------------------------------------------------------------
    fun changeAll(){
        termsAll.value = !termsAll.value!!

        if (termsAll.value!!){
            terms1.value = true
            terms2.value = true
            terms3.value = true
            terms4.value = true
        } else {
            terms1.value = false
            terms2.value = false
            terms3.value = false
            terms4.value = false
        }
        checkBtn1Activate()
    }

    fun checkAll(idx : Int){
        when (idx){
            0 -> { terms1.value = !terms1.value!! }
            1 -> { terms2.value = !terms2.value!! }
            2 -> { terms3.value = !terms3.value!! }
            else -> { terms4.value = !terms4.value!! }
        }
        termsAll.value = (terms1.value!! && terms2.value!! && terms3.value!! && terms4.value!!)
        checkBtn1Activate()
    }

    private fun checkBtn1Activate() {
        btn1Activate.value = (terms1.value!! && terms2.value!! && terms3.value!! )
    }

    // fragment2 (번호 중복확인 페이지) ---------------------------------------------------------------------------------------
    fun checkPhoneNumberForm() {
        btn2Activate.value = phoneNumber.matches(Regex("^[0-9]{3}[0-9]{4}[0-9]{4}\$"))
    }

    // fragment3 (인증번호 입력 페이지) ---------------------------------------------------------------------------------------
    fun checkAuthNumberForm(){
        btn3Activate.value = inputAuthNumber.matches(Regex("[0-9]{6}\$"))
    }

    // fragment4 (닉네임 입력 페이지) ---------------------------------------------------------------------------------------
    fun checkNickNameForm(){
        btn4Activate.value = (inputNickName.length in 1..10)
    }

    // network!! ---------------------------------------------------------------------------------------
    fun tryCheckPhoneNumberDuplicate(){
        btn2Activate.value = false
        CoroutineScope(Dispatchers.IO).launch {
            repository.tryCheckPhoneDuplicate(phoneNumber).let { response ->
                btn2Activate.postValue(true)
                if (response.isSuccessful){
                    apiMessage = response.body()!!.message!!
                    phoneDupResult.postValue(response.body()!!.code)
                }
            }
        }
    }

    fun tryGetAuthNumber(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.setAuthNumber(phoneNumber).let { response ->
                if (response.isSuccessful){
                    apiMessage = response.body()!!.message!!
                    getAuthResult.postValue(response.body()!!.code)
                }
            }
        }
    }

    fun tryCheckAuthNumber(){
        btn3Activate.value = false
        CoroutineScope(Dispatchers.IO).launch {
            repository.tryCheckAuthNumber(phoneNumber, inputAuthNumber).let { response ->
                btn3Activate.postValue(true)
                if (response.isSuccessful){
                    apiMessage = response.body()!!.message!!
                    checkAuthResult.postValue(response.body()!!.code)
                }
            }
        }
    }

    fun tryCheckNickNameDuplicate(){
        btn4Activate.value = false
        CoroutineScope(Dispatchers.IO).launch {
            repository.tryCheckNickName(inputNickName).let { response ->
                btn4Activate.postValue(true)
                if (response.isSuccessful){
                    apiMessage = response.body()!!.message!!
                    nicknameDupResult.postValue(response.body()!!.code)
                }
            }
        }
    }

    fun trySignUp(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.trySignUp(phoneNumber, inputNickName).let { response ->
                if (response.isSuccessful){
                    apiMessage = response.body()!!.message!!
                    if (response.body()!!.code == 200){
                        val edit = GlobalApplication.globalSharedPreferences.edit()
                        edit.putString(GlobalApplication.X_ACCESS_TOKEN, response.body()!!.result.jwt).apply()
                    }
                    signUpResult.postValue(response.body()!!.code)
                }
            }
        }
    }
}
