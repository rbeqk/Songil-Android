package com.songil.songil.page_with.with_story.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.PageCnt

data class ResponseStoryPageIdx(val result : PageCnt) : BaseResponse()

data class ResponseStory(val result : StoryAndArraySize) : BaseResponse()