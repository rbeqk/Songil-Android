package com.example.songil.page_myfavorite_craft

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.songil.config.BaseViewModel
import com.example.songil.recycler.pagingSource.MyFavoriteCraftPagingSource
import kotlinx.coroutines.launch

class MyFavoriteCraftViewModel : BaseViewModel() {
    private val repository = MyFavoriteCraftRepository()

    private val _totalCnt = MutableLiveData<Int>()
    val totalCnt : LiveData<Int> get() = _totalCnt

    fun tryGetFavoriteCraftCnt(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getMyFavoriteCraft(1)
            if (result.body()?.code == 200){
                _totalCnt.postValue(result.body()!!.result.size)
            }
        }
    }

    var flow = Pager(PagingConfig(10)){
        MyFavoriteCraftPagingSource(repository)
    }.flow
}