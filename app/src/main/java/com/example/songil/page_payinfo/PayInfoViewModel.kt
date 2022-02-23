package com.example.songil.page_payinfo

import androidx.lifecycle.*
import com.example.songil.config.BaseViewModel
import com.example.songil.data.Craft4
import com.example.songil.page_payinfo.models.OrderDetailInfo
import kotlinx.coroutines.launch

class PayInfoViewModel(private val orderDetailIdx : Int) : BaseViewModel() {
    private val repository = PayInfoRepository()

    val craftList = arrayListOf<Craft4>()


    private val _getOrderDetailResult = MutableLiveData<Int>()
    val getOrderDetailResult : LiveData<Int> get() = _getOrderDetailResult

    lateinit var orderDetailInfo : OrderDetailInfo

    class PayInfoViewModelFactory(private val orderDetailIdx: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PayInfoViewModel(orderDetailIdx) as T
        }
    }

    fun tryGetOrderDetailInfo(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getOrderDetailInfo(orderDetailIdx)
            if (result.code == 200){
                orderDetailInfo = result.result
                craftList.add(Craft4(craftIdx = orderDetailInfo.craftIdx, mainImageUrl = orderDetailInfo.mainImageUrl, artistName = orderDetailInfo.artistName,
                        name = orderDetailInfo.name, artistIdx = orderDetailInfo.artistIdx, price = orderDetailInfo.price,amount = orderDetailInfo.amount))
            }
            _getOrderDetailResult.postValue(result.code)
        }
    }
}