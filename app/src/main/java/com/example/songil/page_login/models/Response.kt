package com.example.songil.page_login.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Jwt

data class ResponseLogin(val result : Jwt) : BaseResponse()