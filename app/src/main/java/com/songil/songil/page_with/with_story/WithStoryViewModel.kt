package com.songil.songil.page_with.with_story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.songil.songil.config.BaseViewModel
import com.songil.songil.page_with.with_story.paging.WithStoryPagingSource
import kotlinx.coroutines.launch

class WithStoryViewModel : BaseViewModel() {
    private val repository = WithStoryRepository()

    val startIdx = MutableLiveData<Int>()
    private val startIdxInt get() = startIdx.value ?: 0

    var sort = "popular"
    private val sortString get() = sort

    var isRefresh = false

    var pointer : WithStoryPagingSource ?= null
    private fun getWithStoryPagingSource() : WithStoryPagingSource {
        return WithStoryPagingSource(repository, startIdxInt, ::isRefresh, sortString).also { pointer = it }
    }

    var flow = Pager(PagingConfig(10)){
        getWithStoryPagingSource()
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