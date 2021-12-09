package com.example.songil.page_storywrite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoryWriteViewModel : ViewModel() {
    var storyTitle = ""
    var storyContent = ""
    var storyTag = ""
    var writeBtnActivate = MutableLiveData(false)
}