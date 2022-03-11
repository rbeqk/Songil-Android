package com.songil.songil.custom_view.shopping_cart_button

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import kotlinx.coroutines.launch

class ShoppingCartButtonViewModel : BaseViewModel() {
    private val repository = ShoppingCartButtonRepository()

    private val _itemCnt = MutableLiveData<Int>()
    val cartItemCnt : LiveData<Int> get() = _itemCnt

    fun tryGetItemCnt(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getCartItemCnt()
            if (result.body()?.code == 200){
                _itemCnt.postValue(result.body()!!.result.totalCnt)
            }
            else if (result.body()?.code == 2000) {
                _itemCnt.postValue(0)
            }
        }
    }
}