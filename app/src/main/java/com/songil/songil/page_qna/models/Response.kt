package com.songil.songil.page_qna.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Chat
import com.songil.songil.data.LikeData
import com.songil.songil.data.WithQna

data class ResponseQnaChat(val result : ArrayList<Chat>) : BaseResponse()

data class ResponseQna(val result : WithQna) : BaseResponse()

data class ResponseQnaLike(val result : LikeData) : BaseResponse()