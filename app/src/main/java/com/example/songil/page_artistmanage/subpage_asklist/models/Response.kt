package com.example.songil.page_artistmanage.subpage_asklist.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Ask
import com.example.songil.data.PageCnt

data class ResponseArtistAskPageCnt(val result : PageCnt) : BaseResponse()

data class ResponseAskList(val result : ArrayList<Ask>) : BaseResponse()