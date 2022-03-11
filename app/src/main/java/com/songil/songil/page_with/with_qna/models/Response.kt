package com.songil.songil.page_with.with_qna.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.PageCnt
import com.songil.songil.data.WithQna

data class ResponseGetQna(val result : ArrayList<WithQna>) : BaseResponse()

data class ResponseQnaPageCnt(val result : PageCnt) : BaseResponse()