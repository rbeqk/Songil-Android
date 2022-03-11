package com.songil.songil.page_story.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.LikeData
import com.songil.songil.data.WithStory

data class ResponseStoryDetail(val result : WithStory) : BaseResponse()

data class ResponseStoryLike(val result : LikeData) : BaseResponse()