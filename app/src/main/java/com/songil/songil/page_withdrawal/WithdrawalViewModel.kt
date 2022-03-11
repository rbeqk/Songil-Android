package com.songil.songil.page_withdrawal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import kotlinx.coroutines.launch

class WithdrawalViewModel : BaseViewModel() {
    private val repository = WithdrawalRepository()
    var withdrawalActivate = MutableLiveData(false)

    private val _withdrawalResult = MutableLiveData<Int>()
    val withdrawalResult : LiveData<Int> get() = _withdrawalResult

    fun toggleWithdrawalActivate(){
        withdrawalActivate.value = !withdrawalActivate.value!!
    }

    fun tryWithdrawal(){
        viewModelScope.launch(exceptionHandler) {
            _withdrawalResult.postValue(repository.deleteUser())
        }
    }
}