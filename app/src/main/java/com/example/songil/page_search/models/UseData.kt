package com.example.songil.page_search.models

import com.example.songil.data.Craft1
import com.example.songil.data.ItemArticle
import com.example.songil.data.Post

data class RecentlyAndPopularSearchKeywords(val recentlySearch : ArrayList<String>, val popularSearch : ArrayList<String>)

data class ResponseBodyGetSearchShopList(val shopCnt : Int, val withCnt : Int, val articleCnt : Int, val craft : List<Craft1>)

data class ResponseBodyGetSearchWithList(val shopCnt : Int, val withCnt : Int, val articleCnt : Int, val with : List<Post>)

data class ResponseBodyGetSearchArticleList(val shopCnt : Int, val withCnt : Int, val articleCnt : Int, val article : List<ItemArticle>)

enum class SearchCategory {
    SHOP, WITH, ARTICLE
}