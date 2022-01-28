package com.example.songil.page_signup.subpage_term

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.songil.config.BaseViewModel
import com.example.songil.page_signup.models.SignUpInfo

class SignupTermViewModel(private val signUpInfo: SignUpInfo) : BaseViewModel() {

    class SignupTermViewModelFactory(private val signUpInfo: SignUpInfo) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SignupTermViewModel(signUpInfo) as T
        }
    }

    var terms1 = MutableLiveData(false)
    var terms2 = MutableLiveData(false)
    var terms3 = MutableLiveData(false)
    var termsAll = MutableLiveData(false)

    var btnActivate = MutableLiveData(false)

    fun changeAll(){
        termsAll.value = !termsAll.value!!

        if (termsAll.value!!){
            terms1.value = true
            terms2.value = true
            terms3.value = true
        } else {
            terms1.value = false
            terms2.value = false
            terms3.value = false
        }
        checkBtnActivate()
    }

    fun checkAll(idx : Int){
        when (idx){
            0 -> { terms1.value = !terms1.value!! }
            1 -> { terms2.value = !terms2.value!! }
            else -> { terms3.value = !terms3.value!! }
        }
        termsAll.value = (terms1.value!! && terms2.value!! && terms3.value!!)
        checkBtnActivate()
    }

    private fun checkBtnActivate() {
        btnActivate.value = (terms1.value!! && terms2.value!! && terms3.value!! )
    }

    fun setAgreeState() {
        var agreeState = 0
        if (terms1.value!!) agreeState += 1
        if (terms2.value!!) agreeState += 2
        if (terms3.value!!) agreeState += 4
        signUpInfo.termAgree = agreeState
    }
}