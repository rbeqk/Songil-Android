package com.example.songil.page_with.with_abtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.page_abtest.AbtestVoteRepository
import com.example.songil.page_with.with_abtest.paging.WithAbtestPagingSource
import kotlinx.coroutines.launch

class WithAbtestViewModel : BaseViewModel() {
    private val abTestRepository = WithAbtestRepository()
    private val voteRepository = AbtestVoteRepository() // com.example.songil.page_abtest 에 위치

    val startIdx = MutableLiveData<Int>()
    private val startIdxInt get() = startIdx.value ?: 0

    var sort = "popular"
    private val sortString get() = sort

    // 투표, 투표 취소의 결과
    var voteResult = MutableLiveData<Boolean>()

    var isRefresh = false

    var flow = Pager(PagingConfig(pageSize = 5)){
        WithAbtestPagingSource(abTestRepository, startIdxInt, ::isRefresh, sortString)
    }.flow

    fun tryGetPageCnt(){
        viewModelScope.launch(exceptionHandler) {
            startIdx.postValue(abTestRepository.getAbTestPageCnt())
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