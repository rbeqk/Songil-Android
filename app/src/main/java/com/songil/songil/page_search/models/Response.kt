package com.songil.songil.page_search.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.PageCnt

data class ResponseGetSearchKeywords(val result : RecentlyAndPopularSearchKeywords) : BaseResponse()

data class ResponseGetSearchResultPage(val result : PageCnt) : BaseResponse()

data class ResponseGetSearchShopList(val result : ResponseBodyGetSearchShopList) : BaseResponse()

data class ResponseGetSearchWithList(val result : ResponseBodyGetSearchWithList) : BaseResponse()

data class ResponseGetSearchArticleList(val result : ResponseBodyGetSearchArticleList) : BaseResponse()