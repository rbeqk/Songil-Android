package com.songil.songil.page_storywrite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.config.WriteType
import com.songil.songil.page_story.StoryRepository
import com.songil.songil.page_storywrite.models.TagAndUrl
import com.songil.songil.utils.toPlainRequestBody
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class StoryWriteViewModel : BaseViewModel() {
    private val storyWriteRepository = StoryWriteRepository()
    private val storyRepository = StoryRepository()

    // 스토리 수정시 원본 스토리의 story idx
    var storyIdx = -1

    var storyTitle = ""
    var storyContent = ""
    var storyTag = ""
    var tagList = ArrayList<String>()

    private val imageUriList = ArrayList<String>()
    private val imageFileList = ArrayList<File>() // story upload 이후 생성한 이미지 파일을 삭제할 때 사용

    var writeBtnActivate = MutableLiveData(false)
    var tagWritable = MutableLiveData(true)

    var getStoryResult = MutableLiveData<TagAndUrl>()

    var resultUpload = MutableLiveData<Int>()

    fun tryGetStory(){
        viewModelScope.launch(exceptionHandler) {
            val result = storyRepository.getStoryDetail(storyIdx)
            if (result.body()?.code == 200){
                val storyInfo = result.body()!!.result
                storyTitle = storyInfo.title
                storyContent = storyInfo.content
                setImageUriList(storyInfo.imageUrl)
                getStoryResult.postValue(TagAndUrl(storyInfo.tag, storyInfo.imageUrl))
            }
        }
    }

    fun tryUploadStory(isNew : WriteType = WriteType.NEW){
        viewModelScope.launch(exceptionHandler) {
            val fileArray = ArrayList<MultipartBody.Part>()
            for (file in imageFileList){
                val requestBody = file.asRequestBody("image/*".toMediaType())
                fileArray.add(MultipartBody.Part.createFormData("image", file.name, requestBody))
            }
            val hashMap = hashMapOf<String, RequestBody>()
            hashMap["title"] = toPlainRequestBody(storyTitle)
            hashMap["content"] = toPlainRequestBody(storyContent)
            val tag = toPlainRequestBody(tagList)
            if (isNew == WriteType.NEW) {
                storyWriteRepository.uploadStory(hashMap, tag, fileArray).let { response ->
                    resultUpload.postValue(response.body()?.code ?: -1)
                }
            } else {
                storyWriteRepository.modifyStory(storyIdx, hashMap, tag, fileArray).let { response ->
                    resultUpload.postValue(response.body()?.code ?: -1)
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

    fun getImageUriList() = imageUriList

    // remove image file after upload story
    fun clearFiles(){
        for (file in imageFileList){
            if (file.exists()){
                file.delete()
            }
        }
        imageFileList.clear()
    }
}