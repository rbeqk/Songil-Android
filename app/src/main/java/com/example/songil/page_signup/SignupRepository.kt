package com.example.songil.page_signup

import com.example.songil.config.GlobalApplication
import com.example.songil.page_signup.models.RequestAuth
import com.example.songil.page_signup.models.RequestSignUp

class SignupRepository {
    private val signupRetrofitInterface = GlobalApplication.sRetrofit.create(SignupRetrofitInterface::class.java)

    // 새로 갱신된 api
    suspend fun getAgreements() = signupRetrofitInterface.getAgreements()

    suspend fun getAgreementDetail(Idx : Int) = signupRetrofitInterface.getAgreementDetail(Idx)

    suspend fun setAuthNumber(phoneNumber: String) = signupRetrofitInterface.postAuth(RequestAuth(phoneNumber))

    suspend fun tryCheckAuthNumber(phoneNumber: String, authNumber : String) = signupRetrofitInterface.getAuth(phoneNumber, authNumber)

    suspend fun trySignUp(phoneNumber: String, nickname : String) = signupRetrofitInterface.postSignUp(RequestSignUp(phoneNumber, nickname))

    suspend fun tryCheckPhoneDuplicate(phoneNumber: String) = signupRetrofitInterface.getDuplicateCheck(phone = phoneNumber)

    suspend fun tryCheckNickName(nickname: String) = signupRetrofitInterface.getDuplicateCheck(nickname = nickname)
}