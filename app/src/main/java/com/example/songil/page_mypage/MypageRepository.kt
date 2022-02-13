package com.example.songil.page_mypage

import com.example.songil.config.GlobalApplication

class MypageRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(MypageRetrofitInterface::class.java)
}