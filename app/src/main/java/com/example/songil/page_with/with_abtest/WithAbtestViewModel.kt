package com.example.songil.page_with.with_abtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.page_with.with_abtest.paging.WithAbtestPagingSource
import kotlinx.coroutines.launch

class WithAbtestViewModel : BaseViewModel() {
    private val repository = WithAbtestRepository()

    val startIdx = MutableLiveData<Int>()
    private val startIdxInt get() = startIdx.value ?: 0

    var sort = "popular"
    private val sortString get() = sort

    var isRefresh = false

    var flow = Pager(PagingConfig(pageSize = 5)){
        WithAbtestPagingSource(repository, startIdxInt, ::isRefresh, sortString)
    }.flow

    fun tryGetPageCnt(){
        viewModelScope.launch(exceptionHandler) {
            startIdx.postValue(repository.getAbTestPageCnt())
        }
    }
}