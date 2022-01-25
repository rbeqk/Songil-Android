package com.example.songil.page_qnawrite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import kotlinx.coroutines.launch

class QnaWriteViewModel : BaseViewModel() {
    private val repository = QnaWriteRepository()

    var title = ""
    var content = ""
    var writeBtnActivate = MutableLiveData(false)
    var writeQnaResult = MutableLiveData<Boolean>()
    var loadQnaResult = MutableLiveData<Boolean>()
    var qnaIdx = -1

    fun checkActivate(){
        writeBtnActivate.value = (title != "" && content != "")
    }

    fun disableWriteBtn(){
        writeBtnActivate.value = false
    }

    fun tryWriteQna(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.writeQna(title, content)
            writeQnaResult.postValue(result.body()?.code == 200)
        }
    }

    fun tryGetQna(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getQna(qnaIdx)
            title = result.title
            content = result.content
            loadQnaResult.postValue(true)
        }
    }

    fun tryModifyQna(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.modifyQna(qnaIdx, title, content)
            writeQnaResult.postValue(result.body()?.code == 200)
        }
    }
}