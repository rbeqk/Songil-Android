package com.songil.songil.page_craft.subpage_comment

import com.songil.songil.config.GlobalApplication

class CraftCommentRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(CraftCommentRetrofitInterface::class.java)

    suspend fun getCraftComments(craftIdx : Int, page : Int, type : String) = retrofitInterface.getCraftComments(craftIdx, page, type)

    suspend fun getCraftCommentsPageCnt(craftIdx : Int, type : String) = retrofitInterface.getCraftCommentsPageCnt(craftIdx, type)
}