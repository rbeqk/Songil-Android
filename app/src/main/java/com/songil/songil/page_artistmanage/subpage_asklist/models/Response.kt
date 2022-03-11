package com.songil.songil.page_artistmanage.subpage_asklist.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Ask
import com.songil.songil.data.PageCnt

data class ResponseArtistAskPageCnt(val result : PageCnt) : BaseResponse()

data class ResponseAskList(val result : ArrayList<Ask>) : BaseResponse()