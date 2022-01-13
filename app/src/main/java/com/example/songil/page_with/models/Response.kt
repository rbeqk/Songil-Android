package com.example.songil.page_with.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.HotTalk

data class ResponseHotTalk(val result : ArrayList<HotTalk>) : BaseResponse()