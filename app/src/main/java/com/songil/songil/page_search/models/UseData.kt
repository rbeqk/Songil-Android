package com.songil.songil.page_search.models

import com.songil.songil.data.Craft1
import com.songil.songil.data.ItemArticle
import com.songil.songil.data.Post

data class RecentlyAndPopularSearchKeywords(val recentlySearch : ArrayList<String>, val popularSearch : ArrayList<String>)

data class ResponseBodyGetSearchShopList(val shopCnt : Int, val withCnt : Int, val articleCnt : Int, val craft : List<Craft1>)

data class ResponseBodyGetSearchWithList(val shopCnt : Int, val withCnt : Int, val articleCnt : Int, val with : List<Post>)

data class ResponseBodyGetSearchArticleList(val shopCnt : Int, val withCnt : Int, val articleCnt : Int, val article : List<ItemArticle>)

enum class SearchCategory {
    SHOP, WITH, ARTICLE
}