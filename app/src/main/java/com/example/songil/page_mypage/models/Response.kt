package com.example.songil.page_mypage.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.SongilUserInfo

data class ResponseGetMyInfo(val result : SongilUserInfo) : BaseResponse()