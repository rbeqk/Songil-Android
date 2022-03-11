package com.songil.songil.page_signup.models

class SignUpInfo(var requestEmail : String = "", var confirmEmail : String = "", var nickname : String = "", var password : String = "", var termAgree : Int = 0){

    fun checkPassword(inputPassword : String) : Boolean {
        return (password == inputPassword)
    }

    fun confirmEmail() {
         confirmEmail = requestEmail
    }

    fun getRequestForm() : RequestSignUp {
        return RequestSignUp(confirmEmail, password, nickname)
    }
}