package com.example.songil.page_signup.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Jwt

data class ResponseSignUp(val result : Jwt) : BaseResponse()