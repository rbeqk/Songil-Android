package com.example.songil.page_mypage

import com.example.songil.config.GlobalApplication
import com.example.songil.data.PhoneNumber

class MypageRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(MypageRetrofitInterface::class.java)

    suspend fun getUserIdx() = retrofitInterface.getAuthJwt()

    suspend fun getJwt() = retrofitInterface.postLogin(PhoneNumber("11111111111"))
}