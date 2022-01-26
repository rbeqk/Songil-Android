package com.example.songil.page_abtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.insertHeaderItem
import com.example.songil.config.BaseViewModel
import com.example.songil.data.ABTest
import com.example.songil.recycler.pagingSource.PostAndCommentPagingSource
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AbtestViewModel : BaseViewModel() {
    private val abtestRepository = AbtestRepository()
    private val voteRepository = AbtestVoteRepository()

    var abtestIdx = 0
    var loadAbtest = MutableLiveData<Int>()
    private var abTest = ABTest()
    val getAbtest get() = abTest

    var replyPointIdx : Int? = null

    var commentResult = MutableLiveData<Int>()
    var deleteCommentResult = MutableLiveData<Int>()

    var voteResult = MutableLiveData<Boolean>()

    var flow = Pager(PagingConfig(pageSize = 10)) {
        PostAndCommentPagingSource(abtestRepository, abtestIdx)
    }.flow.map { it.insertHeaderItem(item = getAbtest) }

    fun tryGetAbtest(){
        viewModelScope.launch(exceptionHandler) {
            val abtestResult = abtestRepository.getAbtest(abtestIdx)
            abTest = abtestResult
            loadAbtest.postValue(200)
        }
    }

    fun tryWriteComment(comment : String){
        viewModelScope.launch(exceptionHandler) {
            if (comment != ""){
                commentResult.postValue(abtestRepository.writeChat(abtestIdx, replyPointIdx, comment))
            }
            tryGetAbtest()
        }
    }

    fun tryDeleteComment(commentIdx : Int){
        viewModelScope.launch(exceptionHandler) {
            deleteCommentResult.postValue(abtestRepository.deleteChat(commentIdx))
        }
    }

    fun tryVote(abTestIdx : Int, vote : String){
        viewModelScope.launch(exceptionHandler) {
            val result = voteRepository.vote(abTestIdx, vote)
            voteResult.postValue(result.body()?.code == 200)
        }
    }

    fun tryCancelVote(abTestIdx : Int){
        viewModelScope.launch(exceptionHandler) {
            val result = voteRepository.cancelVote(abTestIdx)
            voteResult.postValue(result.body()?.code == 200)
        }
    }
}