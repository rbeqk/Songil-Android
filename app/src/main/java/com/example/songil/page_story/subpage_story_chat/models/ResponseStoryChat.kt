package com.example.songil.page_story.subpage_story_chat.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Chat
import com.example.songil.data.LikeData

data class ResponseStoryChat(val result : ArrayList<Chat>) : BaseResponse()

data class ResponseStoryLike(val result : LikeData)