package com.songil.songil.page_artistmanage.subpage_orderstat

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_artistmanage.subpage_orderstat.models.ResponseGetArtistOrderStatus
import com.songil.songil.page_artistmanage.subpage_orderstat.models.ResponseGetArtistOrderStatusPage

class ArtistManageOrderStatRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ArtistManageOrderStatRetrofitInterface::class.java)

    suspend fun getArtistOrderStatPageCnt() : ResponseGetArtistOrderStatusPage {
        val result = retrofit.getArtistOrderStatusPageCnt()
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun getArtistOrderStat(page : Int) : ResponseGetArtistOrderStatus {
        val result = retrofit.getArtistOrderStatus(page = page)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}