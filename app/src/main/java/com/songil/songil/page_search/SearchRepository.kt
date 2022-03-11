package com.songil.songil.page_search

import com.songil.songil.config.BaseResponse
import com.songil.songil.config.GlobalApplication
import com.songil.songil.page_search.models.*

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

    suspend fun getSearchResultPage(keyword : String, category : String) : ResponseGetSearchResultPage {
        val result = retrofit.getSearchResultPage(keyword = keyword, category = category)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun getSearchShopResult(keyword : String, sort : String, pageIdx : Int) : ResponseGetSearchShopList {
        val result = retrofit.getSearchShopList(keyword = keyword, sort = sort, page = pageIdx)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun getSearchWithResult(keyword : String, sort : String, pageIdx : Int) : ResponseGetSearchWithList {
        val result = retrofit.getSearchWithList(keyword = keyword, sort = sort, page = pageIdx)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }

    suspend fun getSearchArticleResult(keyword : String, sort : String, pageIdx : Int) : ResponseGetSearchArticleList {
        val result = retrofit.getSearchArticleList(keyword = keyword, sort = sort, page = pageIdx)
        if (result.isSuccessful) return result.body()!!
        else throw UnknownError()
    }
}