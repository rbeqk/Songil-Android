package com.songil.songil.page_abtest.model

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.ABTest
import com.songil.songil.data.Chat

// Qna 의 response 와 동일, 나중에 통합 예정
data class ResponseAbtestChat(val result : ArrayList<Chat>) : BaseResponse()

data class ResponseAbtest(val result : ABTest) : BaseResponse()