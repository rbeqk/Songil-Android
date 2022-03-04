package com.example.songil.page_commentwrite

import androidx.lifecycle.*
import com.example.songil.config.BaseViewModel
import com.example.songil.utils.toPlainRequestBody
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class CommentWriteViewModel(private val orderDetailIdx: Int) : BaseViewModel(){

    private val repository = CommentWriteRepository()

    private val _uploadCommentResult = MutableLiveData<Int>()
    val uploadCommentResult : LiveData<Int> get() = _uploadCommentResult

    class CommentWriteViewModelFactory(private val orderDetailIdx : Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CommentWriteViewModel(orderDetailIdx) as T
        }
    }

    fun tryUploadComment(){
        viewModelScope.launch(exceptionHandler) {
            val fileArray = ArrayList<MultipartBody.Part>()
            for (file in imageFileList) {
                val requestBody = file.asRequestBody("image/*".toMediaType())
                fileArray.add(MultipartBody.Part.createFormData("image", file.name, requestBody))
            }
            val hashMap = hashMapOf<String, RequestBody>()
            hashMap["comment"] = toPlainRequestBody(comment)
            hashMap["orderDetailIdx"] = toPlainRequestBody(orderDetailIdx.toString())
            val result = repository.uploadComment(hashMap, fileArray)
            _uploadCommentResult.postValue(result.code)
        }
    }

    var comment = ""
    var commentBtnActivate = MutableLiveData<Boolean>()

    private val imageUriList = ArrayList<String>()
    val imageFileList = ArrayList<File>()

    fun getImageUriList() = imageUriList

    fun checkCommentLength(){
        commentBtnActivate.value = (comment.length in 1..500)
    }

    fun clearFiles(){
        for (file in imageFileList){
            if (file.exists()){
                file.delete()
            }
        }
        imageFileList.clear()
    }
}