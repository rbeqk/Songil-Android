package com.songil.songil.page_mypage_about_post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.songil.songil.config.BaseViewModel
import com.songil.songil.config.MyPageActivityType
import com.songil.songil.data.Post
import com.songil.songil.recycler.pagingSource.PostPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MyPagePostViewModel : BaseViewModel() {
    private val repository = MyPagePostRepository()
    lateinit var flow : Flow<PagingData<Post>>
    private lateinit var type : MyPageActivityType

    private val _totalCnt = MutableLiveData<Int>()
    val totalCnt : LiveData<Int> get() = _totalCnt

    fun setInit(activityType : MyPageActivityType){
        type = activityType
        flow = Pager(PagingConfig(pageSize = 5)){
            PostPagingSource(type, repository)
        }.flow
    }

    fun tryGetCnt(){
        viewModelScope.launch(exceptionHandler) {
            val result = when (type){
                MyPageActivityType.COMMENT_POST -> {
                    repository.getMyCommentPost(1)
                }
                MyPageActivityType.FAVORITE_POST -> {
                    repository.getMyFavoritePost(1)
                }
                MyPageActivityType.MY_POST -> {
                    repository.getMyPost(1)
                }
            }
            if (result.body()?.code == 200){
                _totalCnt.postValue(result.body()!!.result.size)
            }
        }
    }

}