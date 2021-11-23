package com.example.songil.page_signup.models

import com.example.songil.data.Jwt

//data class SignUpResult(val jwt : Jwt)

data class AgreementSimple(val agreementIdx : Int, val title : String, val isRequired : String, val hasContent : Boolean)

data class Agreement(val agreementIdx: Int, val content : String)