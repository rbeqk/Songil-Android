package com.example.songil.page_withdrawal

import com.example.songil.config.BaseResponse
import retrofit2.Response
import retrofit2.http.DELETE

interface WithdrawalRetrofitInterface {
    @DELETE("users")
    suspend fun deleteUser() : Response<BaseResponse>
}