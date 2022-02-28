package com.example.songil.page_notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.data.Notice
import kotlinx.coroutines.launch

class NoticeViewModel : BaseViewModel() {
    private val repository = NoticeRepository()

    private val _getNoticeResult = MutableLiveData<Int>()
    val getNoticeResult : LiveData<Int> get() = _getNoticeResult

    var noticeList = arrayListOf<Notice>()

    fun tryGetNotice() {
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getNotice()
            if (result.code == 200){
                noticeList = result.result
            }
            _getNoticeResult.postValue(result.code)
        }
    }
}