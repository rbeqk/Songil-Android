package com.example.songil.page_mypage_ask_history

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.recycler.pagingSource.MyAskPagingSource

class MyPageAskViewModel : BaseViewModel() {
    private val repository = MyPageAskRepository()

    val flow = Pager(PagingConfig(pageSize = 10)){
        MyAskPagingSource(repository)
    }.flow
}