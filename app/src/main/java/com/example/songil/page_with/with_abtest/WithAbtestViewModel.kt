package com.example.songil.page_with.with_abtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.songil.data.ABTestViewInfo
import com.example.songil.page_with.with_abtest.paging.WithAbtestPagingSource

class WithAbtestViewModel : ViewModel() {
    private val repository = WithAbtestRepository()
    val abTestData = ArrayList<ABTestViewInfo>()
    var loadAbTestResult = MutableLiveData<Int>()
    private var nextPage = 0
    var sort = "popular"

    var flow = Pager(PagingConfig(pageSize = 3)){
        WithAbtestPagingSource(repository, 3)
    }.flow.cachedIn(viewModelScope)

    fun getPageSize(){
        nextPage = 3
    }

    fun tryGetABTestData(){
        val temp = repository.tempGetAbTest(nextPage)
        abTestData.addAll(temp)
        if (nextPage >= 1) nextPage -= 1
        loadAbTestResult.value = 200
    }
}