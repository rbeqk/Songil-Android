package com.example.songil.page_orderstatus

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.recycler.pagingSource.OrdersPagingSource

class OrderStatusViewModel : BaseViewModel() {

    private val repository = OrderStatusRepository()


    var point : OrdersPagingSource ?= null
    private fun getOrdersPagingSource() : OrdersPagingSource {
        return OrdersPagingSource(repository).also { point = it }
    }

    val flow = Pager(PagingConfig(10)){
        getOrdersPagingSource()
    }.flow
}