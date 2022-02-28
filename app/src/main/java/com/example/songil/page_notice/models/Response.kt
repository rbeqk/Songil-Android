package com.example.songil.page_notice.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Notice

data class ResponseGetNotice(val result : ArrayList<Notice>) : BaseResponse()