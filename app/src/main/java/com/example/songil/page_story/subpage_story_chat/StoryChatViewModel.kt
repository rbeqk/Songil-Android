package com.example.songil.page_story.subpage_story_chat

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.recycler.pagingSource.PostAndCommentPagingSource

class StoryChatViewModel : BaseViewModel() {
    private val repository = StoryChatRepository()
    var storyIdx = 0

    var flow = Pager(PagingConfig(10)){
        PostAndCommentPagingSource(repository, storyIdx)
    }.flow

}