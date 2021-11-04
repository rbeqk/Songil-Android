package com.example.songil.page_signup

import com.example.songil.config.GlobalApplication
import com.example.songil.data.PhoneNumber
import com.example.songil.page_signup.models.RequestSignUp

class SignupRepository {
    private val signupRetrofitInterface = GlobalApplication.sRetrofit.create(SignupRetrofitInterface::class.java)

    suspend fun getDuplicateCheck(phoneNumber : String) = signupRetrofitInterface.getPhoneNumberDuplicateCheck(phoneNumber)

    suspend fun getAuthNumber(phoneNumber: String) = signupRetrofitInterface.postAuthPhone(PhoneNumber(phoneNumber))

    suspend fun getDuplicateNickNameCheck(nickName : String) = signupRetrofitInterface.getNickNameDuplicateCheck(nickName)

    suspend fun postSignUp(phoneNumber: String, nickName: String) = signupRetrofitInterface.postSignUp(RequestSignUp(phoneNumber, nickName))
}