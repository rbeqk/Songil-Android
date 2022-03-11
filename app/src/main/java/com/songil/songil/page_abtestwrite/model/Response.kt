package com.songil.songil.page_abtestwrite.model

import com.songil.songil.config.BaseResponse

data class ResponseUploadAbTest(val result : AbTestIdx) : BaseResponse()

data class AbTestIdx(val abTestIdx : Int)