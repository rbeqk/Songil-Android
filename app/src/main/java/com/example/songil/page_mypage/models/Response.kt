package com.example.songil.page_mypage.models

import com.example.songil.config.BaseResponse

data class ResponseLogin(val result : LoginResult) : BaseResponse()

data class ResponseAuthJwt(val result : AuthJwtResult) : BaseResponse()