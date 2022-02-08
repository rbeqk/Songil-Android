package com.example.songil.page_with.with_story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.songil.config.BaseViewModel
import com.example.songil.page_with.with_story.paging.WithStoryPagingSource
import kotlinx.coroutines.launch

class WithStoryViewModel : BaseViewModel() {
    private val repository = WithStoryRepository()

    val startIdx = MutableLiveData<Int>()
    private val startIdxInt get() = startIdx.value ?: 0

    var sort = "popular"
    private val sortString get() = sort

    var isRefresh = false

    var flow = Pager(PagingConfig(10)){
        WithStoryPagingSource(repository, startIdxInt, ::isRefresh, sortString)
    }.flow

    fun tryGetStartIdx(){
        viewModelScope.launch(exceptionHandler) {
            repository.getStoryPageIdx().let { response ->
                if (response.isSuccessful){
                    when (response.body()?.code){
                        200 -> {
                            startIdx.postValue(response.body()!!.result.totalPages)
                        }
                    }
                }
            }
        }
    }
}