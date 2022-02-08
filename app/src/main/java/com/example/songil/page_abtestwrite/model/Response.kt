package com.example.songil.page_abtestwrite.model

import com.example.songil.config.BaseResponse

data class ResponseUploadAbTest(val result : AbTestIdx) : BaseResponse()

data class AbTestIdx(val abTestIdx : Int)