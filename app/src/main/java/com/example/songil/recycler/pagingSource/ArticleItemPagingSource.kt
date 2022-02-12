package com.example.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.ItemArticle
import com.example.songil.page_myfavorite_article.MyFavoriteArticleRepository
import java.lang.Exception

class ArticleItemPagingSource(private val repository : MyFavoriteArticleRepository) : PagingSource<Int, ItemArticle>() {
    override fun getRefreshKey(state: PagingState<Int, ItemArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemArticle> {
        return try {
            val pageIdx = params.key ?: 1
            val response = repository.getFavoriteArticle(pageIdx).body()!!.result
            val prevKey = if (pageIdx == 1) null else pageIdx - 1
            val nextKey = if (response.size == 0) null else pageIdx + 1
            LoadResult.Page(
                    data = response,
                    prevKey = prevKey,
                    nextKey = nextKey
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}