package com.songil.songil.page_artistmanage.subpage_cancel_request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.songil.songil.config.BaseViewModel
import com.songil.songil.config.CancelOrReturn
import com.songil.songil.data.ChangedItemFromAPI
import com.songil.songil.recycler.pagingSource.OrdersCancelRequestPagingSource
import kotlinx.coroutines.launch

class ArtistManageCancelRequestViewModel : BaseViewModel() {
    private val repository = ArtistManageCancelRequestRepository()

    private val _pageCntResult = MutableLiveData<Int>()
    val pageCntResult : LiveData<Int> get() = _pageCntResult

    private val _requestAnswerResult = MutableLiveData<ChangedItemFromAPI<Int>>()
    val requestAnswerResult : LiveData<ChangedItemFromAPI<Int>> get() = _requestAnswerResult

    var startPage = 0
    var errorMsg = ""

    lateinit var pagingSourcePointer : OrdersCancelRequestPagingSource

    fun tryGetPageCnt(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getCancelRequestPage()
            if (result.code == 200){
                startPage = result.result.totalPages
            }
            _pageCntResult.postValue(result.code)
        }
    }

    fun trySendRequestAnswer(cancelOrReturn: CancelOrReturn, isApprove : Boolean, orderDetailIdx : Int, positionInAdapter : Int){
        viewModelScope.launch(exceptionHandler) {
            val result = when(cancelOrReturn){
                CancelOrReturn.CANCEL -> {
                    if (isApprove)  {
                        repository.postCancelRequestAnswer(orderDetailIdx = orderDetailIdx, "approval")
                    } else {
                        repository.postCancelRequestAnswer(orderDetailIdx = orderDetailIdx, "rejection")
                    }
                }
                CancelOrReturn.RETURN -> {
                    if (isApprove)  {
                        repository.postReturnRequestAnswer(orderDetailIdx = orderDetailIdx, "approval")
                    } else {
                        repository.postReturnRequestAnswer(orderDetailIdx = orderDetailIdx, "rejection")
                    }
                }
            }
            errorMsg = result.message ?: ""
            _requestAnswerResult.postValue(ChangedItemFromAPI(position = positionInAdapter, ApiResultCode = result.code, newData = orderDetailIdx))
        }
    }

    private fun getPagingSource() : OrdersCancelRequestPagingSource {
        return OrdersCancelRequestPagingSource(repository, startPage).also { pagingSourcePointer = it }
    }

    var flow = Pager(PagingConfig(pageSize = 10)){
        getPagingSource()
    }.flow
}