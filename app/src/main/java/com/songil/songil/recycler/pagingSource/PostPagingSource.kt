package com.songil.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.songil.songil.config.MyPageActivityType
import com.songil.songil.data.Post
import com.songil.songil.page_mypage_about_post.MyPagePostRepository
import java.lang.Exception

class PostPagingSource(private val type : MyPageActivityType, private val repository : MyPagePostRepository) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val pageIdx = params.key ?: 1
            val response = when(type){
                MyPageActivityType.MY_POST -> {repository.getMyPost(pageIdx)}
                MyPageActivityType.FAVORITE_POST -> { repository.getMyFavoritePost(pageIdx)}
                MyPageActivityType.COMMENT_POST -> {repository.getMyCommentPost(pageIdx)}
            }
            val prevKey = if (pageIdx == 1) null else pageIdx - 1
            val nextKey = if (response.body()!!.result.size == 0) null else pageIdx + 1
            LoadResult.Page(
                    data = response.body()!!.result,
                    prevKey = prevKey,
                    nextKey = nextKey
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}