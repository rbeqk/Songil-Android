package com.example.songil.page_myfavorite_craft

import com.example.songil.config.GlobalApplication
import com.example.songil.page_myfavorite_craft.models.ResponseFavoriteCrafts
import retrofit2.Response

class MyFavoriteCraftRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(MyFavoriteCraftRetrofitInterface::class.java)

    suspend fun getMyFavoriteCraft(pageIdx : Int) : Response<ResponseFavoriteCrafts>{
        val result = retrofitInterface.getLikedCrafts(page = pageIdx)
        if (result.isSuccessful){
            return result
        }
        else throw UnknownError()
    }
}