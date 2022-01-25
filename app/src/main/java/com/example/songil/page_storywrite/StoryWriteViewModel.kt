package com.example.songil.page_storywrite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.utils.toPlainRequestBody
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class StoryWriteViewModel : BaseViewModel() {
    private val repository = StoryWriteRepository()

    var storyTitle = ""
    var storyContent = ""
    var storyTag = ""
    var tagList = ArrayList<String>()

    private val imageUriList = ArrayList<String>()
    private val imageFileList = ArrayList<File>() // story upload 이후 생성한 이미지 파일을 삭제할 때 사용

    var writeBtnActivate = MutableLiveData(false)
    var tagWritable = MutableLiveData(true)

    var resultUpload = MutableLiveData<Int>()

    fun tryUploadStory(){
        viewModelScope.launch {
            val fileArray = ArrayList<MultipartBody.Part>()
            for (file in imageFileList){
                val requestBody = file.asRequestBody("image/*".toMediaType())
                fileArray.add(MultipartBody.Part.createFormData("image", file.name, requestBody))
            }
            val hashMap = hashMapOf<String, RequestBody>()
            hashMap["title"] = toPlainRequestBody(storyTitle)
            hashMap["content"] = toPlainRequestBody(storyContent)
            val tag = toPlainRequestBody(tagList)
            repository.uploadStory(hashMap, tag, fileArray).let { response ->
                if (response.isSuccessful){
                    resultUpload.postValue(response.body()?.code ?: -1)
                } else {
                    resultUpload.postValue(-1)
                }
            }
        }
    }

    fun setImageUriList(newImageUriList : ArrayList<String>){
        imageUriList.clear()
        imageUriList.addAll(newImageUriList)
    }

    fun checkAvailable(){
        writeBtnActivate.value = ((storyTitle != "" && storyContent != "" && tagList.size < 4 && imageUriList.size > 0))
    }

    fun checkTagCount(){
        tagWritable.value = (tagList.size in 0..2)
    }

    fun setFiles(fileList : ArrayList<File>){
        imageFileList.clear()
        imageFileList.addAll(fileList)
    }

    // remove image file after upload story
    fun clearFiles(){
        for (file in imageFileList){
            if (file.exists()){
                file.delete()
            }
        }
    }
}