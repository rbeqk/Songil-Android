package com.songil.songil.page_artist.subpage_craft

import com.songil.songil.page_artist.subpage_craft.models.ResponseArtistItemCnt
import com.songil.songil.page_artist.subpage_craft.models.ResponseCraftList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistCraftRetrofitInterface {
    @GET("artists/{artistIdx}/crafts/page")
    suspend fun getArtistCraftCnt(@Path("artistIdx") artistIdx : Int) : Response<ResponseArtistItemCnt>

    @GET("artists/{artistIdx}/crafts")
    suspend fun getCraftList(@Path("artistIdx") artistIdx : Int, @Query("page") page : Int, @Query("sort") sort : String) : Response<ResponseCraftList>
}