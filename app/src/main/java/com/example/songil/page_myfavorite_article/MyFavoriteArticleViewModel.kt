package com.example.songil.page_myfavorite_article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.recycler.pagingSource.ArticleItemPagingSource
import kotlinx.coroutines.launch

class MyFavoriteArticleViewModel : BaseViewModel() {
    private val repository = MyFavoriteArticleRepository()

    private val _totalCnt = MutableLiveData<Int>()
    val totalCnt : LiveData<Int> get() = _totalCnt

    fun tryCheckArticleEmpty(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getFavoriteArticle(1)
            if (result.body()?.code == 200) _totalCnt.postValue(result.body()!!.result.size)
        }
    }

    var flow = Pager(PagingConfig(pageSize = 10)){
        ArticleItemPagingSource(repository)
    }.flow
}