package com.example.songil.page_with.with_abtest.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.ABTest
import com.example.songil.data.PageCnt

data class ResponseGetAbTest(val result : ArrayList<ABTest>) : BaseResponse()

data class ResponseAbTestPageCnt(val result : PageCnt) : BaseResponse()