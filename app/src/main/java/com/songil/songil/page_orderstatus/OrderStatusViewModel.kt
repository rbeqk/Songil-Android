package com.songil.songil.page_orderstatus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.songil.songil.config.BaseViewModel
import com.songil.songil.recycler.pagingSource.OrdersPagingSource
import kotlinx.coroutines.launch

class OrderStatusViewModel : BaseViewModel() {

    private val repository = OrderStatusRepository()

    private val _itemIsEmpty = MutableLiveData<Boolean>()
    val itemIsEmpty : LiveData<Boolean> get() = _itemIsEmpty

    var point : OrdersPagingSource ?= null
    private fun getOrdersPagingSource() : OrdersPagingSource {
        return OrdersPagingSource(repository).also { point = it }
    }

    fun tryCheckItemEmpty(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getOrderStatus(1)
            _itemIsEmpty.postValue(result.size == 0)
        }
    }

    val flow = Pager(PagingConfig(10)){
        getOrdersPagingSource()
    }.flow
}