package com.example.songil.page_splash.models

import com.example.songil.config.BaseResponse

data class ResponseAuthLogin(val result : AuthLoginResult) : BaseResponse()

data class ResponseAuthJwt(val result : AuthJwtResult) : BaseResponse()