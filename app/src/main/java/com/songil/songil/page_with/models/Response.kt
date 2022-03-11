package com.songil.songil.page_with.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.HotTalk

data class ResponseHotTalk(val result : ArrayList<HotTalk>) : BaseResponse()