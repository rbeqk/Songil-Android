package com.songil.songil.page_storywrite.models

import com.songil.songil.config.BaseResponse

data class ResponseUploadStory(val result : StoryIdx) : BaseResponse()

data class StoryIdx(val storyIdx : Int)