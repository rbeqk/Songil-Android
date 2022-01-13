package com.example.songil.page_with.with_qna

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.songil.page_with.with_qna.paging.WithQnaPagingSource

class WithQnaViewModel : ViewModel() {
    private val repository = WithQnaRepository()
    var sort = "popular"

    var flow = Pager(PagingConfig(pageSize = 20)){
        WithQnaPagingSource(repository, 20)
    }.flow.cachedIn(viewModelScope)
}