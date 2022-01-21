package com.example.songil.page_story.subpage_story_chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.recycler.pagingSource.PostAndCommentPagingSource
import kotlinx.coroutines.launch

class StoryChatViewModel : BaseViewModel() {
    private val repository = StoryChatRepository()
    var storyIdx = 0

    var registerResult = MutableLiveData<Int>()

    var replyPointIdx : Int? = null
    var deleteCommentResult = MutableLiveData<Int>()

    var flow = Pager(PagingConfig(10)){
        PostAndCommentPagingSource(repository, storyIdx)
    }.flow

    fun tryWriteComment(comment : String){
        viewModelScope.launch(exceptionHandler) {
            if (comment != "") {
                registerResult.postValue(repository.writeChat(storyIdx, replyPointIdx, comment))
            }
        }
    }

    fun tryDeleteComment(commentIdx : Int){
        viewModelScope.launch(exceptionHandler) {
            deleteCommentResult.postValue(repository.deleteChat(commentIdx))
        }
    }
}