package com.example.songil.page_qna


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.insertHeaderItem
import com.example.songil.config.BaseViewModel
import com.example.songil.data.WithQna
import com.example.songil.recycler.pagingSource.PostAndCommentPagingSource
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QnaViewModel : BaseViewModel() {
    private val repository = QnaRepository()
    var qnaIdx = 0
    var loadQna = MutableLiveData<Int>()
    private var qna : WithQna = WithQna(0, 0, null, "",
            "", "", "", "N", 0, "N", 0)
    val getQna get() = qna

    var replyPointIdx : Int? = null

    var commentResult = MutableLiveData<Int>()
    var deleteCommentResult = MutableLiveData<Int>()
    var changeLikeResult = MutableLiveData<Boolean>()
    var deleteQnaResult = MutableLiveData<Boolean>()

    var flow = Pager(PagingConfig(pageSize = 10)){
        PostAndCommentPagingSource(repository, qnaIdx)
    }.flow.map { it.insertHeaderItem(item = getQna) }


    fun tryGetQna() {
        viewModelScope.launch(exceptionHandler) {
            val qnaResult = repository.getQna(qnaIdx)
            if (qnaResult.body()?.code == 200){
                qna = qnaResult.body()!!.result
            }
            loadQna.postValue(qnaResult.body()?.code ?: -1)
        }
    }

    fun tryWriteComment(comment : String){
        viewModelScope.launch(exceptionHandler) {
            if (comment != ""){
                commentResult.postValue(repository.writeChat(qnaIdx, replyPointIdx, comment))
            }
            tryGetQna()
        }
    }

    fun tryRemoveComment(commentIdx : Int){
        viewModelScope.launch(exceptionHandler) {
            deleteCommentResult.postValue(repository.deleteChat(commentIdx))
        }
    }

    fun tryToggleLike(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.changeQnaLike(qnaIdx)
            if (result.body()?.code == 200){
                qna.totalLikeCnt = result.body()!!.result.totalLikeCnt
                qna.isLike = result.body()!!.result.isLike
                changeLikeResult.postValue(true)
            } else {
                changeLikeResult.postValue(false)
            }
        }
    }

    fun getIsWriter() : Boolean {
        return getQna.isUserQnA == "Y"
    }

    fun tryRemoveQna() {
        viewModelScope.launch(exceptionHandler) {
            val result = repository.deleteQna(qnaIdx)
            if (result.body()?.code == 200){
                deleteQnaResult.postValue(true)
            } else {
                deleteQnaResult.postValue(false)
            }
        }
    }
}