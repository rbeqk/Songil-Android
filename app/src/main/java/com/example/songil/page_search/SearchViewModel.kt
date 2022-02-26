package com.example.songil.page_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    private val repository = SearchRepository()

    val recentlyKeywords = ArrayList<String>()
    val popularKeywords = ArrayList<String>()

    private val _getKeywordsResult = MutableLiveData<Int>()
    val getKeywordsResult : LiveData<Int> get() = _getKeywordsResult

    private val _removeResult = MutableLiveData<Int>()
    val removeResult : LiveData<Int> get() = _removeResult

    fun tryGetSearchKeywords(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getSearchKeywords()
            recentlyKeywords.clear()
            recentlyKeywords.addAll(result.result.recentlySearch)
            popularKeywords.clear()
            popularKeywords.addAll(result.result.popularSearch)
            _getKeywordsResult.postValue(result.code)
        }
    }

    fun tryRemoveAllRecentlyKeywords(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.deleteAllRecentlyKeywords()
            _removeResult.postValue(result.code)
        }
    }

    fun tryRemoveWord(keyword : String){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.deleteKeyword(keyword = keyword)
            _removeResult.postValue(result.code)
        }
    }
}