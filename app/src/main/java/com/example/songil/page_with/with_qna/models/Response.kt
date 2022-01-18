package com.example.songil.page_with.with_qna.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.PageCnt
import com.example.songil.data.WithQna

data class ResponseGetQna(val result : ArrayList<WithQna>) : BaseResponse()

data class ResponseQnaPageCnt(val result : PageCnt) : BaseResponse()