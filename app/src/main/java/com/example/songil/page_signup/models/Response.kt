package com.example.songil.page_signup.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.AuthNumber

data class ResponseAuthPhone(val result : AuthNumber) : BaseResponse()

data class ResponseSignUp(val result : SignUpResult) : BaseResponse()