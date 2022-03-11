package com.songil.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.songil.songil.data.Craft1
import com.songil.songil.data.ItemArticle
import com.songil.songil.data.Post
import com.songil.songil.page_search.SearchRepository

class SearchShopPagingSource(private val repository : SearchRepository,
                         private val keyword : String,
                         private var startIdx : Int,
                         private val sort : String, private val changeCnt : (Int, Int, Int) -> Unit)
    : PagingSource<Int, Craft1>(){

    override fun getRefreshKey(state: PagingState<Int, Craft1>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Craft1> {
        return try {
            val pageIdx = params.key ?: startIdx
            val response = repository.getSearchShopResult(keyword = keyword, sort = sort, pageIdx = pageIdx)
            changeCnt(response.result.shopCnt, response.result.withCnt, response.result.articleCnt)
            val nextKey = if (pageIdx <= 1) null else pageIdx - 1
            val prevKey = if (pageIdx == startIdx) null else pageIdx + 1
            LoadResult.Page(
                    data = response.result.craft,
                    prevKey = prevKey,
                    nextKey = nextKey
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}

class SearchWithPagingSource(private val repository : SearchRepository,
                             private val keyword : String,
                             private var startIdx : Int,
                             private val sort : String, private val changeCnt : (Int, Int, Int) -> Unit)
    : PagingSource<Int, Post>(){

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val pageIdx = params.key ?: startIdx
            val response = repository.getSearchWithResult(keyword = keyword, sort = sort, pageIdx = pageIdx)
            changeCnt(response.result.shopCnt, response.result.withCnt, response.result.articleCnt)
            val nextKey = if (pageIdx <= 1) null else pageIdx - 1
            val prevKey = if (pageIdx == startIdx) null else pageIdx + 1
            LoadResult.Page(
                    data = response.result.with,
                    prevKey = prevKey,
                    nextKey = nextKey
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}

class SearchArticlePagingSource(private val repository : SearchRepository,
                             private val keyword : String,
                             private var startIdx : Int,
                             private val sort : String, private val changeCnt : (Int, Int, Int) -> Unit)
    : PagingSource<Int, ItemArticle>(){

    override fun getRefreshKey(state: PagingState<Int, ItemArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemArticle> {
        return try {
            val pageIdx = params.key ?: startIdx
            val response = repository.getSearchArticleResult(keyword = keyword, sort = sort, pageIdx = pageIdx)
            changeCnt(response.result.shopCnt, response.result.withCnt, response.result.articleCnt)
            val nextKey = if (pageIdx <= 1) null else pageIdx - 1
            val prevKey = if (pageIdx == startIdx) null else pageIdx + 1
            LoadResult.Page(
                    data = response.result.article,
                    prevKey = prevKey,
                    nextKey = nextKey
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}