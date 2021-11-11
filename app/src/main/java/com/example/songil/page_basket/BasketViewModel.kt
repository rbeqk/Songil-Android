package com.example.songil.page_basket

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.page_basket.models.BasketItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BasketViewModel : ViewModel() {
    private val repository = BasketRepository()
    val itemList = ArrayList<BasketItem>()
    var paymentBtnActivate = MutableLiveData<Boolean>(false)
    var itemResultCode = MutableLiveData<Int>()
    var checkAll = MutableLiveData<Boolean>(false)

    /*fun tempFunction(){
        itemList.add(BasketItem(1, 1, "테스트상품1", "작가1", 1, 35000, "https://kougeihin.jp/wp/wp-content/uploads/craft0629.jpg"))
        itemList.add(BasketItem(1, 1, "테스트상품2", "작가1", 2, 30000, "https://kougeihin.jp/wp/wp-content/uploads/craft0629.jpg"))
        itemList.add(BasketItem(1, 1, "테스트상품3", "작가2", 1, 28000, "https://kougeihin.jp/wp/wp-content/uploads/craft0629.jpg"))
        itemList.add(BasketItem(1, 1, "테스트상품4", "작가1", 1, 34000, "https://kougeihin.jp/wp/wp-content/uploads/craft0629.jpg"))
        itemList.add(BasketItem(1, 1, "테스트상품5", "작가3", 3, 40000, "https://kougeihin.jp/wp/wp-content/uploads/craft0629.jpg"))
        itemResultCode.value = 1000
    }*/

    fun tryGetCart(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getCartItems().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1000){
                        itemList.addAll(response.body()!!.result)
                    }
                    itemResultCode.postValue(response.body()!!.code)
                }
            }
        }
    }

    fun tryChangeItemCount(position : Int){
        CoroutineScope(Dispatchers.IO).launch {
            repository.changeItemCount(itemList[position].cartIdx, itemList[position].amount).let { response ->
                if (response.isSuccessful){
                    Log.d("change item", response.body()!!.message!!)
                }
            }
        }
    }

    fun tryDeleteItem(position : Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteItem(itemList[position].cartIdx).let { response ->
                if (response.isSuccessful){
                    Log.d("delete item", response.body()!!.message!!)
                }
            }
        }
    }


    // get checked item total price
    fun getTotalPrice() : Int {
        var total = 0
        for (item in itemList){
            if (item.checked)
                total += (item.price * item.amount)
        }
        return total
    }

    // get checked item count
    fun getCheckCount() : Int {
        var total = 0
        for (item in itemList){
            if (item.checked) total += 1
        }
        return total
    }

    // get all item is checked
    fun checkAllCbSelected() : Boolean {
        var flag = true
        for (item in itemList){
            if (!item.checked){
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
}