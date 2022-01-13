package com.example.songil.page_with.with_story.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.PageCnt

data class ResponseStoryPageIdx(val result : PageCnt) : BaseResponse()

data class ResponseStory(val result : StoryAndArraySize) : BaseResponse()