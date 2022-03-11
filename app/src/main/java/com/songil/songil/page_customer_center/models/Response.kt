package com.songil.songil.page_customer_center.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Notice

data class ResponseGetFnq(val result : ArrayList<Notice>) : BaseResponse()