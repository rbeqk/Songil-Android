package com.songil.songil.page_qnawrite.models

import com.songil.songil.config.BaseResponse

data class ResponseWriteQna(val result : QnaIdx) : BaseResponse()

data class QnaIdx(val qnaIdx : Int)