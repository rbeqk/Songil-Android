package com.example.songil.page_story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.data.WithStory
import kotlinx.coroutines.launch

class StoryViewModel : BaseViewModel() {
    private val repository = StoryRepository()
    lateinit var storyDetail : WithStory
    var storyIdx = 0

    var storyDetailResult = MutableLiveData<Int>()
    var likeResult = MutableLiveData<Boolean>()
    var removeResult = MutableLiveData<Boolean>()

    fun tryGetStoryDetail(inputStoryIdx : Int = 0){
        storyIdx = inputStoryIdx
        viewModelScope.launch(exceptionHandler) {
            repository.getStoryDetail(storyIdx).let { response ->
                if (response.isSuccessful){
                    if (response.body()?.code == 200){
                        storyDetail = response.body()!!.result
                    }
                }
                storyDetailResult.postValue(response.body()?.code ?: -1)
            }
        }
    }

    fun tryToggleLike() {
        viewModelScope.launch {
            val result = repository.getStoryLike(storyIdx)
            if (result != null){
                storyDetail.isLike = result.isLike
                storyDetail.totalLikeCnt = result.totalLikeCnt
                likeResult.postValue(true)
            } else {
                likeResult.postValue(false)
            }

        }
    }

    fun tryDeleteStory(){
        viewModelScope.launch {
            removeResult.postValue(repository.delStory(storyIdx))
        }
    }

}