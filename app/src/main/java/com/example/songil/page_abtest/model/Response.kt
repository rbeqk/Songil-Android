package com.example.songil.page_abtest.model

import com.example.songil.config.BaseResponse
import com.example.songil.data.ABTest
import com.example.songil.data.Chat

// Qna 의 response 와 동일, 나중에 통합 예정
data class ResponseAbtestChat(val result : ArrayList<Chat>) : BaseResponse()

data class ResponseAbtest(val result : ABTest) : BaseResponse()