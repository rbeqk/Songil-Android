package com.songil.songil.page_story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.data.FrontStory
import com.songil.songil.data.WithStory
import kotlinx.coroutines.launch

class StoryViewModel : BaseViewModel() {
    private val repository = StoryRepository()
    lateinit var storyDetail : WithStory
    var storyIdx = 0

    var storyDetailResult = MutableLiveData<Int>()
    var likeResult = MutableLiveData<Boolean>()
    var removeResult = MutableLiveData<Boolean>()

    fun getFrontStory() : FrontStory {
        return FrontStory(
            mainImageUrl = storyDetail.imageUrl[0],
            isLike = storyDetail.isLike,
            totalLikeCnt = storyDetail.totalLikeCnt,
            title = storyDetail.title,
            userName = storyDetail.userName,
            userIdx = storyDetail.userIdx,
            storyIdx = storyDetail.storyIdx
        )
    }

    fun tryGetStoryDetail(){
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
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getStoryLike(storyIdx)
            if (result.body()?.code == 200){
                storyDetail.isLike = result.body()!!.result.isLike
                storyDetail.totalLikeCnt = result.body()!!.result.totalLikeCnt
                likeResult.postValue(true)
            } else {
                likeResult.postValue(false)
            }

        }
    }

    fun tryDeleteStory(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.delStory(storyIdx)
            removeResult.postValue(result.body()?.code == 200)
        }
    }

}