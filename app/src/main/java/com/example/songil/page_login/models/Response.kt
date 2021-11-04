package com.example.songil.page_login.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.AuthNumber

data class ResponseAuthPhone(val result : AuthNumber) : BaseResponse()

data class ResponseLogin(val result : LoginResult) : BaseResponse()