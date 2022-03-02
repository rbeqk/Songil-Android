package com.example.songil.page_search.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.PageCnt

data class ResponseGetSearchKeywords(val result : RecentlyAndPopularSearchKeywords) : BaseResponse()

data class ResponseGetSearchResultPage(val result : PageCnt) : BaseResponse()

data class ResponseGetSearchShopList(val result : ResponseBodyGetSearchShopList) : BaseResponse()

data class ResponseGetSearchWithList(val result : ResponseBodyGetSearchWithList) : BaseResponse()

data class ResponseGetSearchArticleList(val result : ResponseBodyGetSearchArticleList) : BaseResponse()