package com.songil.songil.page_artistmanage.subpage_cancel_request.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.PageCnt

data class ResponseGetCancelRequestPage(val result : PageCnt) : BaseResponse()

data class ResponseGetCancelRequestItemList(val result : ArrayList<GetCancelRequestItemResponseBody>) : BaseResponse()