package com.example.songil.page_artistmanage.subpage_cancel_request

import com.example.songil.page_artistmanage.subpage_cancel_request.models.ResponseGetCancelRequestItemList
import com.example.songil.page_artistmanage.subpage_cancel_request.models.ResponseGetCancelRequestPage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistManageCancelRequestRetrofitInterface {
    @GET("artist-page/orders/page")
    suspend fun getCancelRequestPageCnt(@Query("type") type : String = "cancelOrReturn") : Response<ResponseGetCancelRequestPage>

    @GET("artist-page/orders")
    suspend fun getCancelRequestItems(@Query("type") type : String = "cancelOrReturn", @Query("page") page : Int) : Response<ResponseGetCancelRequestItemList>
}