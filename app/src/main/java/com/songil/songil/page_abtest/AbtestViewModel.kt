package com.songil.songil.page_abtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.insertHeaderItem
import com.songil.songil.config.BaseViewModel
import com.songil.songil.data.ABTest
import com.songil.songil.page_with.WithRepository
import com.songil.songil.recycler.pagingSource.PostAndCommentPagingSource
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AbtestViewModel : BaseViewModel() {
    private val abtestRepository = AbtestRepository()
    private val voteRepository = AbtestVoteRepository()
    private val blockRepository = WithRepository()

    var abtestIdx = 0
    var loadAbtest = MutableLiveData<Int>()
    private var abTest = ABTest()
    val getAbtest get() = abTest

    var replyPointIdx : Int? = null

    var commentResult = MutableLiveData<Int>()
    var deleteCommentResult = MutableLiveData<Int>()

    var voteResult = MutableLiveData<Boolean>()
    var deleteResult = MutableLiveData<Int>()

    var blockWriterResult = MutableLiveData<Boolean>()
    var blockCommentUserResult = MutableLiveData<Boolean>()

    var flow = Pager(PagingConfig(pageSize = 10)) {
        PostAndCommentPagingSource(abtestRepository, abtestIdx)
    }.flow.map { it.insertHeaderItem(item = getAbtest) }

    fun tryGetAbtest(){
        viewModelScope.launch(exceptionHandler) {
            val abtestResult = abtestRepository.getAbtest(abtestIdx)
            if (abtestResult.body()?.code == 200){
                abTest = abtestResult.body()!!.result
            }
            loadAbtest.postValue(abtestResult.body()?.code ?: -1)
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

    fun tryDeleteAbTest(){
        viewModelScope.launch(exceptionHandler) {
            val result = abtestRepository.deleteAbTest(abtestIdx)
            deleteResult.postValue(result.body()?.code ?: -1)
        }
    }

    fun tryBlockWriter(){
        viewModelScope.launch(exceptionHandler) {
            val result = blockRepository.postBlockUser(abTest.userIdx)
            blockWriterResult.postValue(result == 200)
        }
    }

    fun tryBlockUserByChat(targetUserIdx : Int){
        viewModelScope.launch(exceptionHandler) {
            val result = blockRepository.postBlockUser(targetUserIdx)
            blockCommentUserResult.postValue(result == 200)
        }
    }
}