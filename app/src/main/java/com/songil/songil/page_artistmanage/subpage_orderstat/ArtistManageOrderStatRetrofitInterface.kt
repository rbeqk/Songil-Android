package com.songil.songil.page_artistmanage.subpage_orderstat

import com.songil.songil.page_artistmanage.subpage_orderstat.models.ResponseGetArtistOrderStatus
import com.songil.songil.page_artistmanage.subpage_orderstat.models.ResponseGetArtistOrderStatusPage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistManageOrderStatRetrofitInterface {
    @GET("artist-page/orders/page")
    suspend fun getArtistOrderStatusPageCnt(@Query("type") type : String = "basic") : Response<ResponseGetArtistOrderStatusPage>

    @GET("artist-page/orders")
    suspend fun getArtistOrderStatus(@Query("type") type : String = "basic", @Query("page") page : Int) : Response<ResponseGetArtistOrderStatus>

}