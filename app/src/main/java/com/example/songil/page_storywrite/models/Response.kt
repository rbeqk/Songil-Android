package com.example.songil.page_storywrite.models

import com.example.songil.config.BaseResponse

data class ResponseUploadStory(val result : StoryIdx) : BaseResponse()

data class StoryIdx(val storyIdx : Int)