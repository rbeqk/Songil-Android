package com.songil.songil.page_craft.subpage_comment

import com.songil.songil.page_craft.subpage_comment.models.ResponseCraftComment
import com.songil.songil.page_craft.subpage_comment.models.ResponseCraftCommentPageCnt
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CraftCommentRetrofitInterface {
    @GET("shop/crafts/{craftIdx}/comments")
    suspend fun getCraftComments(@Path("craftIdx") craftIdx : Int, @Query("page") page : Int, @Query("type") type: String) : Response<ResponseCraftComment>

    @GET("shop/crafts/{craftIdx}/comments/page")
    suspend fun getCraftCommentsPageCnt(@Path("craftIdx") craftIdx : Int, @Query("type") type : String) : Response<ResponseCraftCommentPageCnt>
}