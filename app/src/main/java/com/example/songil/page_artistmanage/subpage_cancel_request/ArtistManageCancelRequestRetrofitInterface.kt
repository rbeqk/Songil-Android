package com.example.songil.page_artistmanage.subpage_cancel_request

import com.example.songil.config.BaseResponse
import com.example.songil.page_artistmanage.subpage_cancel_request.models.RequestBodyPostCancelOrReturn
import com.example.songil.page_artistmanage.subpage_cancel_request.models.ResponseGetCancelRequestItemList
import com.example.songil.page_artistmanage.subpage_cancel_request.models.ResponseGetCancelRequestPage
import retrofit2.Response
import retrofit2.http.*

interface ArtistManageCancelRequestRetrofitInterface {
    @GET("artist-page/orders/page")
    suspend fun getCancelRequestPageCnt(@Query("type") type : String = "cancelOrReturn") : Response<ResponseGetCancelRequestPage>

    @GET("artist-page/orders")
    suspend fun getCancelRequestItems(@Query("type") type : String = "cancelOrReturn", @Query("page") page : Int) : Response<ResponseGetCancelRequestItemList>

    @POST("artist-page/orders/{orderDetailIdx}/cancel")
    suspend fun postCancelOrder(@Path("orderDetailIdx") orderDetailIdx : Int, @Body params : RequestBodyPostCancelOrReturn) : Response<BaseResponse>
}