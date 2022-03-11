package com.songil.songil.page_story.subpage_story_chat.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Chat
import com.songil.songil.data.LikeData

data class ResponseStoryChat(val result : ArrayList<Chat>) : BaseResponse()

data class ResponseStoryLike(val result : LikeData)