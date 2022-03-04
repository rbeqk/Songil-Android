package com.example.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.Comment
import kotlin.reflect.KSuspendFunction1

// 오름차순!
class CommentPagingSource(private val load : KSuspendFunction1<Int, List<Comment>>) : PagingSource<Int, Comment>(){
    override fun getRefreshKey(state: PagingState<Int, Comment>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {
        return try {
            val pageIdx = params.key ?: 1
            val response = load(pageIdx)
            val prevKey = if (pageIdx == 1) null else pageIdx - 1
            val nextKey = if (response.isEmpty()) null else pageIdx + 1
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