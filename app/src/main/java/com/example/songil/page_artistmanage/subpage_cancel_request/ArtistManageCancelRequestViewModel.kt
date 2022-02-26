package com.example.songil.page_artistmanage.subpage_cancel_request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.recycler.pagingSource.OrdersCancelRequestPagingSource
import kotlinx.coroutines.launch

class ArtistManageCancelRequestViewModel : BaseViewModel() {
    private val repository = ArtistManageCancelRequestRepository()

    private val _pageCntResult = MutableLiveData<Int>()
    val pageCntResult : LiveData<Int> get() = _pageCntResult

    var startPage = 0

    lateinit var pagingSourcePointer : OrdersCancelRequestPagingSource

    fun tryGetPageCnt(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getCancelRequestPage()
            if (result.code == 200){
                startPage = result.result.totalPages
            }
            _pageCntResult.postValue(result.code)
        }
    }

    private fun getPagingSource() : OrdersCancelRequestPagingSource {
        return OrdersCancelRequestPagingSource(repository, startPage).also { pagingSourcePointer = it }
    }

    var flow = Pager(PagingConfig(pageSize = 10)){
        getPagingSource()
    }.flow
}