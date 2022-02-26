package com.example.songil.page_artistmanage.subpage_cancel_request.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.PageCnt

data class ResponseGetCancelRequestPage(val result : PageCnt) : BaseResponse()

data class ResponseGetCancelRequestItemList(val result : ArrayList<GetCancelRequestItemResponseBody>) : BaseResponse()