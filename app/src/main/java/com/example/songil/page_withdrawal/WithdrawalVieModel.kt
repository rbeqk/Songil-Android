package com.example.songil.page_withdrawal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WithdrawalVieModel : ViewModel() {
    var withdrawalActivate = MutableLiveData<Boolean>(false)

    fun toggleWithdrawalActivate(){
        withdrawalActivate.value = !withdrawalActivate.value!!
    }
}