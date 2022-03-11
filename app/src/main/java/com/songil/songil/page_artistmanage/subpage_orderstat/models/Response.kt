package com.songil.songil.page_artistmanage.subpage_orderstat.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.PageCnt

data class ResponseGetArtistOrderStatus(val result : ArrayList<GetArtistOrderStatusResponseBody>) : BaseResponse()

data class ResponseGetArtistOrderStatusPage(val result : PageCnt) : BaseResponse()