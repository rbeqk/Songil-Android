package com.example.songil.page_basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.data.CraftAndAmount
import com.example.songil.page_basket.models.AmountAndPosition
import com.example.songil.page_basket.models.CartItem
import kotlinx.coroutines.launch

class BasketViewModel : BaseViewModel() {
    private val repository = BasketRepository()
    val itemList = ArrayList<CartItem>()
    var paymentBtnActivate = MutableLiveData(true)
    var itemResultCode = MutableLiveData<Int>()
    var checkAll = MutableLiveData<Boolean>(false)

    private val _removeItemResult = MutableLiveData<Int>()
    val removeItemResult : LiveData<Int> get() = _removeItemResult

    private val _amountChangeResult = MutableLiveData<AmountAndPosition>()
    val amountChangeResult : LiveData<AmountAndPosition> get() = _amountChangeResult

    private val _cartItemCnt = MutableLiveData<Int>()
    val cartItemCnt : LiveData<Int> get() = _cartItemCnt

    fun tryGetCartItem(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getCartItem()
            if (result.body()?.code == 200){
                itemList.clear()
                itemList.addAll(result.body()!!.result)
                _cartItemCnt.postValue(result.body()!!.result.size)
                checkAll.postValue(true)
            }
            itemResultCode.postValue(result.body()?.code ?: -1)
        }
    }

    private fun tryChangeItemCount(position : Int, amountChange : Int){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.changeItemAmount(itemList[position].craftIdx, amountChange)
            if (result.body()?.code == 200){
                itemList[position].amount = result.body()!!.result.amount
                _amountChangeResult.postValue(AmountAndPosition(result.body()!!.result.amount, position))
            }
        }
    }

    private fun tryDeleteItem(position : Int) {
        viewModelScope.launch(exceptionHandler) {
            val craftIdx = itemList[position].craftIdx
            val result = repository.deleteItem(craftIdx)
            if (result.body()?.code == 200){
                itemList.removeAt(position)
                _cartItemCnt.postValue(itemList.size)
            }
            _removeItemResult.postValue(result.body()?.code ?: -1)
        }
    }


    // get checked item total price ( amount * price )
    fun getTotalPrice() : Int {
        var total = 0
        for (item in itemList){
            if (item.checked != false)
                total += (item.price * item.amount)
        }
        return total
    }

    // get checked item count (used for checkedItemText [ex. 1/3])
    fun getCheckCount() : Int {
        var total = 0
        for (item in itemList){
            if (item.checked != false) total += 1
        }
        return total
    }

    // check all item is checked (apply isChecked in cbAll)
    private fun checkAllCbSelected() : Boolean {
        var flag = true
        for (item in itemList){
            if (item.checked == false){
                flag = false
                break
            }
        }
        return flag
    }

    // change all item check status
    fun changeCbAll(){
        checkAll.value = !checkAll.value!!
        for (i in 0 until itemList.size){
            itemList[i].checked = checkAll.value!!
        }
    }

    // change item's checked
    fun toggleCheckButton(position : Int){
        if (itemList[position].checked == null) itemList[position].checked = true
        itemList[position].checked = !itemList[position].checked!!
        checkAll.value = checkAllCbSelected()
    }

    fun changeItemAmount(position : Int, isPlus : Boolean){
        if (isPlus && itemList[position].amount < 9){
            tryChangeItemCount(position, 1)
        } else if (!isPlus && itemList[position].amount > 1){
            tryChangeItemCount(position, -1)
        }
    }

    fun getOrderCraftForm() : ArrayList<CraftAndAmount> {
        val temp = ArrayList<CraftAndAmount>()
        for (item in itemList){
            temp.add(CraftAndAmount(item.craftIdx, item.amount))
        }
        return temp
    }

    fun removeItem(position: Int){
        tryDeleteItem(position)
    }
}