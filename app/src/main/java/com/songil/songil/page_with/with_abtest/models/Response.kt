package com.songil.songil.page_with.with_abtest.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.ABTest
import com.songil.songil.data.PageCnt

data class ResponseGetAbTest(val result : ArrayList<ABTest>) : BaseResponse()

data class ResponseAbTestPageCnt(val result : PageCnt) : BaseResponse()