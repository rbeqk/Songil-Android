package com.songil.songil.page_withdrawal

import com.songil.songil.config.GlobalApplication

class WithdrawalRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(WithdrawalRetrofitInterface::class.java)

    suspend fun deleteUser() : Int {
        val result = retrofit.deleteUser()
        if (result.isSuccessful) return result.body()!!.code
        else throw UnknownError()
    }
}