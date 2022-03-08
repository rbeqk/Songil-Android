package com.example.songil.page_artistmanage.subpage_asklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.page_artistmanage.subpage_asklist.paging.Craft3ArtistPagingSource
import kotlinx.coroutines.launch

class ArtistManageAskListViewModel : BaseViewModel() {
    private val repository = ArtistManageAskListRepository()

    private val _pageCntResult = MutableLiveData<Int>()
    val pageCntResult : LiveData<Int> get() = _pageCntResult

    var startIdx = 20
    var isRefresh = false

    var flow = Pager(PagingConfig(pageSize = 5)){
        Craft3ArtistPagingSource(repository, startIdx)
    }.flow

    fun tryGetPageCnt(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getTotalPageCnt()
            if (result.body()?.code == 200){
                startIdx = result.body()!!.result.totalPages
                _pageCntResult.postValue(startIdx)
            }
        }
    }
}