package com.example.songil.page_search

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.page_search.models.ResponseGetSearchKeywords

class SearchRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(SearchRetrofitInterface::class.java)

    suspend fun getSearchKeywords() : ResponseGetSearchKeywords {
        val result = retrofit.getSearchKeywords()
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun deleteAllRecentlyKeywords() : BaseResponse {
        val result = retrofit.deleteAllRecentlyKeywords()
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun deleteKeyword(keyword : String) : BaseResponse {
        val result = retrofit.deleteKeywords(word = keyword)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}