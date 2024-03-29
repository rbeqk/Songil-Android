package com.songil.songil.page_cancel

import com.songil.songil.config.BaseResponse
import com.songil.songil.page_cancel.models.RequestBodyPostOrderCancel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface CancelRetrofitInterface {
    @POST("my-page/orders/{orderDetailIdx}/cancel")
    suspend fun postOrderCancel(@Path("orderDetailIdx") orderDetailIdx : Int, @Body params : RequestBodyPostOrderCancel) : Response<BaseResponse>
}