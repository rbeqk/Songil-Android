package com.songil.songil.page_with.with_qna

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.songil.songil.config.BaseViewModel
import com.songil.songil.page_with.with_qna.paging.WithQnaPagingSource
import kotlinx.coroutines.launch

class WithQnaViewModel : BaseViewModel() {
    private val repository = WithQnaRepository()

    val startIdx = MutableLiveData<Int>()
    private val startIdxInt get() = startIdx.value ?: 0

    var sort = "popular"
    private val sortString get() = sort

    var isRefresh = false

    var flow = Pager(PagingConfig(pageSize = 20)){
        WithQnaPagingSource(repository, startIdxInt, ::isRefresh, sortString)
    }.flow

    fun tryGetPageCnt(){
        viewModelScope.launch(exceptionHandler) {
            startIdx.postValue(repository.getQnaPageCnt())
        }
    }
}