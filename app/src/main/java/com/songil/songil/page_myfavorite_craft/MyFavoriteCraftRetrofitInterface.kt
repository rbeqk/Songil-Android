package com.songil.songil.page_myfavorite_craft

import com.songil.songil.page_myfavorite_craft.models.ResponseFavoriteCrafts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyFavoriteCraftRetrofitInterface {
    @GET("my-page/crafts/liked")
    suspend fun getLikedCrafts(@Query("page") page : Int) : Response<ResponseFavoriteCrafts>
}