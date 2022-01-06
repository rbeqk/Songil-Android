package com.example.songil.page_artistmanage.subpage_asklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.songil.page_artistmanage.subpage_asklist.paging.Craft3ArtistPagingSource

class ArtistManageAskListViewModel : ViewModel() {
    private val repository = ArtistManageAskListRepository()
    var startIdx = 20
    var isRefresh = false

    var flow = Pager(PagingConfig(pageSize = 6)){
        Craft3ArtistPagingSource(repository, ::startIdx, ::isRefresh)
    }.flow.cachedIn(viewModelScope)

    fun refresh(){
        isRefresh = true
        startIdx = 10
    }
}