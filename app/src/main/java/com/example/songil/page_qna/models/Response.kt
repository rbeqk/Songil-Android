package com.example.songil.page_qna.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Chat
import com.example.songil.data.WithQna

data class ResponseQnaChat(val result : ArrayList<Chat>) : BaseResponse()

data class ResponseQna(val result : WithQna) : BaseResponse()