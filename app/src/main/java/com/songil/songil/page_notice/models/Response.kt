package com.songil.songil.page_notice.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Notice

data class ResponseGetNotice(val result : ArrayList<Notice>) : BaseResponse()