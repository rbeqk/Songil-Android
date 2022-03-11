package com.songil.songil.page_signup.models

data class RequestSignUp(val email : String, val password : String, val nickname : String)

data class RequestAuth(val email : String)