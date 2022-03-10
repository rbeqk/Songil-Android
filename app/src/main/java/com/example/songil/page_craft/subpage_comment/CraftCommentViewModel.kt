package com.example.songil.page_craft.subpage_comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.data.CraftComment
import kotlinx.coroutines.launch
import kotlin.math.max

class CraftCommentViewModel : BaseViewModel() {
    private val repository = CraftCommentRepository()

    var updateResult = MutableLiveData<Int>()
    var craftCommentList = ArrayList<CraftComment>()
    var craftCommentPageCnt = MutableLiveData<Int>()
    var photoOnly = MutableLiveData<Boolean>(false)

    var newDataCnt = 0
    private var requestPage = 0
    private var craftIdx = 0

    fun setCraftIdx(idx : Int) {
        craftIdx = idx
    }

    fun tryGetComments(){
        if (requestPage > 0) {
            viewModelScope.launch(exceptionHandler) {
                repository.getCraftComments(craftIdx, requestPage, changeTypeToString()).let { response ->
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            craftCommentList.addAll(response.body()!!.result.comments)
                            newDataCnt = response.body()!!.result.comments.size
                            requestPage = max(0, requestPage - 1)
                        } else {
                            newDataCnt = 0
                        }
                    }
                    updateResult.postValue(response.body()?.code ?: -1)
                }
            }
        }
    }

    fun tryGetCommentFirst(){
        if (requestPage > 0) {
            viewModelScope.launch(exceptionHandler) {
                var tempDataCnt = 0
                repository.getCraftComments(craftIdx, requestPage, changeTypeToString()).let { response ->
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            craftCommentList.addAll(response.body()!!.result.comments)
                            tempDataCnt += response.body()!!.result.comments.size
                            requestPage = max(0, requestPage - 1)
                        }
                    }
                }
                repository.getCraftComments(craftIdx, requestPage, changeTypeToString()).let { response ->
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            craftCommentList.addAll(response.body()!!.result.comments)
                            tempDataCnt += response.body()!!.result.comments.size
                            requestPage = max(0, requestPage - 1)
                        } else {
                            newDataCnt = 0
                        }
                    }
                    newDataCnt = tempDataCnt
                    updateResult.postValue(response.body()?.code ?: -1)
                }
            }
        }
    }

    fun tryGetCommentsPageCnt(){
        viewModelScope.launch(exceptionHandler) {
            repository.getCraftCommentsPageCnt(craftIdx, changeTypeToString()).let { response ->
                if (response.isSuccessful){
                    if (response.body()?.code == 200){
                        craftCommentList.clear()
                        requestPage = response.body()!!.result.totalPages
                        craftCommentPageCnt.postValue(requestPage)
                    } else {
                        craftCommentPageCnt.postValue(0)
                    }
                } else {
                    craftCommentPageCnt.postValue(0)
                }
            }
        }
    }

    fun changePhoto(){
        photoOnly.value = !photoOnly.value!!
    }

    private fun changeTypeToString() : String {
        return if (photoOnly.value == true){ "photo" } else { "all" }
    }
}