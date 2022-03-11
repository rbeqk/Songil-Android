package com.songil.songil.page_artistmanage.subpage_asklist

import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_artistmanage.subpage_asklist.models.ResponseArtistAskPageCnt
import com.songil.songil.page_artistmanage.subpage_asklist.models.ResponseAskList
import retrofit2.Response


class ArtistManageAskListRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ArtistManageAskRetrofitInterface::class.java)

    suspend fun getTotalPageCnt() : Response<ResponseArtistAskPageCnt>{
        val result = retrofit.getTotalPageCnt()
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun getAskList(pageIdx : Int) : Response<ResponseAskList>{
        val result = retrofit.getAskList(page = pageIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}