package com.songil.songil.page_mycomment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.songil.songil.config.BaseViewModel
import com.songil.songil.recycler.pagingSource.CommentPagingSource
import com.songil.songil.recycler.pagingSource.CraftSimplePagingSource
import kotlinx.coroutines.launch

class MycommentViewModel : BaseViewModel() {
    private val repository = MycommentRepository()

    var btnWriteable = MutableLiveData(true)
    var btnWritten = MutableLiveData(false)
    var currentFragmentIdx = MutableLiveData<Int>()

    val writableCommentFlow = Pager(PagingConfig(pageSize = 10)){
        CraftSimplePagingSource(::getWritablePagingData)
    }.flow

    val writtenCommentFlow = Pager(PagingConfig(pageSize = 10)){
        CommentPagingSource(::getWrittenPagingData)
    }.flow

    // 작성 가능한 코멘트가 비어있는지 여부
    private val _writableCommentIsEmpty = MutableLiveData<Boolean>()
    val writableCommentIsEmpty : LiveData<Boolean> get() = _writableCommentIsEmpty

    // 작성 완료한 코멘트가 비어있는지 여부
    private val _writtenCommentIsEmpty = MutableLiveData<Boolean>()
    val writtenCommentIsEmpty : LiveData<Boolean> get() = _writtenCommentIsEmpty

    // 코멘트 제거 api 호출 결과 code 값
    private val _deleteCommentResult = MutableLiveData<Int>()
    val deleteCommentResult : LiveData<Int> get() = _deleteCommentResult

    // 임시적으로 저장하게 되는 삭제 대상 commentIdx 와 해당 comment 의 recyclerView position
    var removeTargetCommentIdx = 0
    var removeTargetCommentPosition = 0

    private suspend fun getWritablePagingData(pageIdx : Int) = repository.getMyWritableComment(pageIdx)
    private suspend fun getWrittenPagingData(pageIdx : Int) = repository.getMyWrittenComment(pageIdx)

    // 작성 가능한 코멘트 개수 호출
    // 1 페이지의 아이템 개수를 호출하여 해당 개수가 0이면 비어있는것으로 간주
    fun tryGetWritableCommentCnt() {
        viewModelScope.launch(exceptionHandler) {
            _writableCommentIsEmpty.postValue(repository.getMyWritableCommentCnt() == 0)
        }
    }

    // 작성 완료한 코멘트 개수 호출
    // 1 페이지의 아이템 개수를 호출하여 해당 개수가 0이면 비어있는것으로 간주
    fun tryGetWrittenCommentCnt(){
        viewModelScope.launch(exceptionHandler) {
            _writtenCommentIsEmpty.postValue(repository.getMyWrittenCommentCnt() == 0)
        }
    }

    // 작성 가능한 코멘트 <-> 작성 완료한 코멘트 화면 전환
    // categoryIdx 가 0이면 작성 가능한 코멘트,
    // 1이면 작성 완료한 코멘트 표시
    fun changeCategory(categoryIdx : Int){
        currentFragmentIdx.value = categoryIdx
        when (categoryIdx){
            0 -> {
                btnWriteable.value = true
                btnWritten.value = false
            }
            else -> {
                btnWriteable.value = false
                btnWritten.value = true
            }
        }
    }

    // 댓글 삭제 api
    fun tryDeleteComment(){
        viewModelScope.launch(exceptionHandler) {
            _deleteCommentResult.postValue(repository.deleteComment(removeTargetCommentIdx))
        }
    }
}
