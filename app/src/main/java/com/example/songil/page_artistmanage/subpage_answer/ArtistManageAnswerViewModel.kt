package com.example.songil.page_artistmanage.subpage_answer

import android.util.Log
import androidx.lifecycle.*
import com.example.songil.config.BaseViewModel
import com.example.songil.data.AskDetail
import kotlinx.coroutines.launch

class ArtistManageAnswerViewModel(private val askIdx : Int, answerStat : Int) : BaseViewModel() {

    private val repository = ArtistManageAnswerRepository()
    private val notAnswerYet = (answerStat == 1)

    class ArtistManageAnswerViewModelFactory(private val askIdx : Int, private val stat : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ArtistManageAnswerViewModel(askIdx, stat) as T
        }
    }

    var btnActivate = MutableLiveData(false)

    private val _inquiryContent = MutableLiveData<AskDetail>()
    val inquiryContent : LiveData<AskDetail> get()= _inquiryContent

    private val _sendAnswerResult = MutableLiveData<Boolean>()
    val sendAnswerResult : LiveData<Boolean> get() = _sendAnswerResult


    var answerContent = ""

    fun tryGetInquiry(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getAskDetailInfo(askIdx)
            if (result.body()?.code == 200){
                answerContent = result.body()!!.result.answerContent ?: ""
                Log.d("answerContent", answerContent)
                _inquiryContent.postValue(result.body()!!.result)
            }
        }
    }

    fun trySendAnswer(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.sendAnswer(askIdx, answerContent)
            _sendAnswerResult.postValue(result.body()?.code == 200)
        }
    }

    fun checkActivate(){
        btnActivate.value = (answerContent != "") && notAnswerYet
    }
}