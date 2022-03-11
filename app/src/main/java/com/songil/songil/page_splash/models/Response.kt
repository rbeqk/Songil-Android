package com.songil.songil.page_splash.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Jwt
import com.songil.songil.data.UserType

data class ResponseAutoLoginBody(val result : Jwt) : BaseResponse()

data class ResponseGetUserTypeBody(val result : UserType) : BaseResponse()