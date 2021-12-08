package com.example.songil.page_with.with_story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.songil.page_with.with_story.paging.WithStoryPagingSource

class WithStoryViewModel : ViewModel() {
    private val repository = WithStoryRepository()
    // lateinit var flow : Flow<PagingData<FrontStory>>
    var flow = Pager(PagingConfig(pageSize = 20)){
        WithStoryPagingSource(repository, 20)
    }.flow.cachedIn(viewModelScope)

}