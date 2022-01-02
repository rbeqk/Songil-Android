package com.example.songil.page_notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.songil.page_notice.paging.NoticePagingSource

class NoticeViewModel : ViewModel() {
    private val repository = NoticeRepository()

    var flow = Pager(PagingConfig(5)){
        NoticePagingSource(repository, 5)
    }.flow.cachedIn(viewModelScope)
}