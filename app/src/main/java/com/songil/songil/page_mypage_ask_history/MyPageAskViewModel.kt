package com.songil.songil.page_mypage_ask_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.songil.songil.config.BaseViewModel
import com.songil.songil.recycler.pagingSource.MyAskPagingSource
import kotlinx.coroutines.launch

class MyPageAskViewModel : BaseViewModel() {
    private val repository = MyPageAskRepository()

    private val _itemCntIsZero = MutableLiveData<Boolean>()
    val itemCntIsZero : LiveData<Boolean> get() = _itemCntIsZero

    val flow = Pager(PagingConfig(pageSize = 10)){
        MyAskPagingSource(repository)
    }.flow

    fun tryCheckAskListCntIsZero(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getMyAskList(1)
            if (result.code == 200){
                _itemCntIsZero.postValue(result.result.size == 0)
            }
        }
    }
}