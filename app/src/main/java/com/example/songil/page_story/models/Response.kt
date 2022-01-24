package com.example.songil.page_story.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.LikeData
import com.example.songil.data.WithStory

data class ResponseStoryDetail(val result : WithStory) : BaseResponse()

data class ResponseStoryLike(val result : LikeData) : BaseResponse()