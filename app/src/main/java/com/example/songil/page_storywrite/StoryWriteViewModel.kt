package com.example.songil.page_storywrite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songil.utils.toPlainRequestBody
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class StoryWriteViewModel : ViewModel() {
    private val repository = StoryWriteRepository()

    var storyTitle = ""
    var storyContent = ""
    var storyTag = ""
    var tagList = ArrayList<String>()

    private val imagePathList = ArrayList<String>()
    private val imageFileList = ArrayList<File>()

    var writeBtnActivate = MutableLiveData(false)
    var tagWritable = MutableLiveData(true)

    var resultUpload = MutableLiveData<Int>()

    // upload story by image file path (local)
    fun tryUploadStoryUsePath(){
        viewModelScope.launch {
            val fileArray = ArrayList<MultipartBody.Part>()
            for (image in imagePathList){
                val file = File(image)
                val requestBody = file.asRequestBody("image/*".toMediaType())
                fileArray.add(MultipartBody.Part.createFormData("image", file.name, requestBody))
            }
            val hashMap = hashMapOf<String, RequestBody>()
            hashMap["title"] = toPlainRequestBody(storyTitle)
            hashMap["content"] = toPlainRequestBody(storyContent)
            val tag = toPlainRequestBody(tagList)
            repository.uploadStory(hashMap, tag, fileArray).let { response ->
                if (response.isSuccessful){
                    resultUpload.postValue(200)
                }
            }
        }
    }

    // upload story by bitmap from imageView
    fun tryUploadStoryUseFile(){
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
                    resultUpload.postValue(200)
                }
            }
        }
    }

    fun setFileList(newFileList : ArrayList<File>){
        imageFileList.clear()
        imageFileList.addAll(newFileList)
    }

    fun setImageList(newImageList : ArrayList<String>){
        imagePathList.clear()
        imagePathList.addAll(newImageList)
    }

    fun checkAvailable(){
        writeBtnActivate.value = ((storyTitle != "" && storyContent != "" && tagList.size < 4 && imagePathList.size > 0))
    }

    fun getImageLen() : Int {
        return imagePathList.size
    }

    fun checkTagCount(){
        tagWritable.value = (tagList.size in 0..2)
    }
}