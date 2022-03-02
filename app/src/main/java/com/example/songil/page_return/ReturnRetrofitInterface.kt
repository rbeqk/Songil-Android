package com.example.songil.page_return

import com.example.songil.config.BaseResponse
import com.example.songil.page_return.models.RequestBodyPostReturn
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReturnRetrofitInterface {
    @POST("my-page/orders/{orderDetailIdx}/return")
    suspend fun postReturnRequest(@Path("orderDetailIdx") orderDetailIdx : Int, @Body params : RequestBodyPostReturn) : Response<BaseResponse>
}