package com.songil.songil.page_mypage.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.SongilUserInfo

data class ResponseGetMyInfo(val result : SongilUserInfo) : BaseResponse()