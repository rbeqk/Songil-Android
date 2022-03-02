package com.example.songil.page_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.data.SearchItemCountList
import com.example.songil.page_search.models.SearchCategory
import com.example.songil.recycler.pagingSource.SearchArticlePagingSource
import com.example.songil.recycler.pagingSource.SearchShopPagingSource
import com.example.songil.recycler.pagingSource.SearchWithPagingSource
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {
    private val repository = SearchRepository()

    val recentlyKeywords = ArrayList<String>()
    val popularKeywords = ArrayList<String>()

    // 사용자 검색 기록 및 인기 검색어 호출 결과
    private val _getKeywordsResult = MutableLiveData<Int>()
    val getKeywordsResult : LiveData<Int> get() = _getKeywordsResult

    // 검색어 삭제 api 호출 결과
    private val _removeResult = MutableLiveData<Int>()
    val removeResult : LiveData<Int> get() = _removeResult

    // 페이징 적용시 시작 page 인덱스
    private val _startIdx = MutableLiveData<Int>()
    val startIdx : LiveData<Int> get() = _startIdx

    // 상단 toolbar 에 표기되는 각 검색 결과의 count 표시를 위한 데이터
    private val _cntList = MutableLiveData<SearchItemCountList>()
    val cntList : LiveData<SearchItemCountList> get() = _cntList

    // 카테고리를 enum 클래스와 string 으로 연결한 것
    private val mapCategory = mapOf(SearchCategory.ARTICLE to "article", SearchCategory.SHOP to "shop", SearchCategory.WITH to "with")

    // editText 에 입력된 text (data binding 적용)
    var inputKeyword : String = ""

    // 현재 보여지고 있는 카테고리 화면
    var searchCategory = SearchCategory.SHOP

    // 현재 정렬된 기준
    var sort = "popular"

    val shopPagingFlow = Pager(PagingConfig(pageSize = 10)){
        SearchShopPagingSource(repository = repository, keyword = inputKeyword, startIdx = (startIdx.value!!).coerceAtLeast(1), sort = sort, ::changeSearchItemCountList)
    }.flow

    var withPagingFlow = Pager(PagingConfig(pageSize = 10)){
        SearchWithPagingSource(repository = repository, keyword = inputKeyword, startIdx = (startIdx.value!!).coerceAtLeast(1), sort = sort, ::changeSearchItemCountList)
    }.flow

    var articlePagingFlow = Pager(PagingConfig(pageSize = 10)){
        SearchArticlePagingSource(repository = repository, keyword = inputKeyword, startIdx = (startIdx.value!!).coerceAtLeast(1), sort = sort, ::changeSearchItemCountList)
    }.flow

    var shopBtnActivate = MutableLiveData(false)
    var withBtnActivate = MutableLiveData(false)
    var articleBtnActivate = MutableLiveData(false)

    // 화면 상단에 보여질 각 검색 결과의 개수 갱신
    private fun changeSearchItemCountList(shopCnt : Int, withCnt : Int, articleCnt : Int){
        _cntList.postValue(SearchItemCountList(shopCnt, withCnt, articleCnt))
    }

    //
    fun changeCategory(inputCategory : SearchCategory){
        searchCategory = inputCategory
        shopBtnActivate.value = (searchCategory == SearchCategory.SHOP)
        withBtnActivate.value = (searchCategory == SearchCategory.WITH)
        articleBtnActivate.value = (searchCategory == SearchCategory.ARTICLE)
    }


    // 검색 기록 및 인기 검색어 호출 (기존 데이터 모두 삭제 후 갱신)
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
    // 모든 사용자 검색어 삭제
    fun tryRemoveAllRecentlyKeywords(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.deleteAllRecentlyKeywords()
            _removeResult.postValue(result.code)
        }
    }

    // 사용자 검색어 삭제
    fun tryRemoveWord(keyword : String){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.deleteKeyword(keyword = keyword)
            _removeResult.postValue(result.code)
        }
    }

    // 검색 결과의 시작 페이지 인덱스 호출
    fun tryGetSearchPageCnt() {
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getSearchResultPage(keyword = inputKeyword, category = mapCategory[searchCategory]!!)
            if (result.code == 200){
                _startIdx.postValue(result.result.totalPages)
            } else {
                _startIdx.postValue(-1) // ERROR
            }
        }
    }
}