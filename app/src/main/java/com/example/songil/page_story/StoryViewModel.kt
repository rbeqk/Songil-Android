package com.example.songil.page_story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.WithStory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryViewModel : ViewModel() {
    private val repository = StoryRepository()
    lateinit var storyDetail : WithStory
    var storyIdx = 0

    var storyDetailResult = MutableLiveData<Int>()

    fun tryGetStoryDetail(inputStoryIdx : Int = 0){
        storyIdx = inputStoryIdx
        CoroutineScope(Dispatchers.IO).launch {
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
}