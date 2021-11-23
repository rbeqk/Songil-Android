package com.example.songil.page_signup.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.AuthNumber
import com.example.songil.data.Jwt

data class ResponseAuthPhone(val result : AuthNumber) : BaseResponse()

data class ResponseSignUp(val result : Jwt) : BaseResponse()

data class ResponseAgreements(val result : AgreementSimple) : BaseResponse()

data class ResponseAgreementDetail(val result : Agreement) : BaseResponse()