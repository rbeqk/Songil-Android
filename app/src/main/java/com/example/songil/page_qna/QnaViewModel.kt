package com.example.songil.page_qna

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertHeaderItem
import com.example.songil.config.BaseViewModel
import com.example.songil.data.Post
import com.example.songil.recycler.pagingSource.PostAndCommentPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QnaViewModel : BaseViewModel() {
    private val repository = QnaRepository()
    var qnaIdx = 0
    var loadQna = MutableLiveData<Int>()
    lateinit var flow : Flow<PagingData<Post>>

    fun getQna() {
        viewModelScope.launch(exceptionHandler) {
            val qna = repository.getQna(qnaIdx)
            if (qna != null){
                flow = Pager(PagingConfig(pageSize = 10)){
                    PostAndCommentPagingSource(repository, qnaIdx)
                }.flow.map { it.insertHeaderItem(item = qna) }
                loadQna.postValue(200)
            } else {
                loadQna.postValue(-1)
            }
        }
    }

}