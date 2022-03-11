package com.songil.songil.page_signup.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Jwt

data class ResponseSignUp(val result : Jwt) : BaseResponse()