package com.songil.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.songil.songil.page_mypage_ask_history.MyPageAskRepository
import com.songil.songil.page_mypage_ask_history.models.MyPageAskTotalData
import java.lang.Exception

class MyAskPagingSource(private val repository : MyPageAskRepository) : PagingSource<Int, MyPageAskTotalData>() {
    override fun getRefreshKey(state: PagingState<Int, MyPageAskTotalData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MyPageAskTotalData> {
        return try {
            val pageIdx = params.key ?: 1
            val response = repository.getMyAskList(pageIdx).result
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