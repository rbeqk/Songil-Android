package com.example.songil.page_splash.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Jwt

data class ResponseAutoLoginBody(val result : Jwt) : BaseResponse()

data class ResponseGetUserTypeBody(val type : Int) : BaseResponse()