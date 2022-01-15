package com.example.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.Post
import com.example.songil.repository.PostRepository
import java.lang.Exception

class PostAndCommentPagingSource(private val repository : PostRepository, private val postIdx : Int = 0) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val pageIdx = params.key ?: 1
            val response = repository.getChat(postIdx, pageIdx)
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