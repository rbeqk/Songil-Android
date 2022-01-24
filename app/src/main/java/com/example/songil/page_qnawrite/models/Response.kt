package com.example.songil.page_qnawrite.models

import com.example.songil.config.BaseResponse

data class ResponseWriteQna(val result : QnaIdx) : BaseResponse()

data class QnaIdx(val qnaIdx : Int)