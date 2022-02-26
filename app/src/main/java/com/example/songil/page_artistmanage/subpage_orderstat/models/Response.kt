package com.example.songil.page_artistmanage.subpage_orderstat.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.PageCnt

data class ResponseGetArtistOrderStatus(val result : ArrayList<GetArtistOrderStatusResponseBody>) : BaseResponse()

data class ResponseGetArtistOrderStatusPage(val result : PageCnt) : BaseResponse()