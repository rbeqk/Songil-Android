package com.example.songil.page_with.with_abtest.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.ABTestViewInfo
import com.example.songil.page_with.with_abtest.WithAbtestRepository
import java.lang.Exception

class WithAbtestPagingSource(private val repository : WithAbtestRepository, private var startIdx : Int) : PagingSource<Int, ABTestViewInfo>() {
    override fun getRefreshKey(state: PagingState<Int, ABTestViewInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ABTestViewInfo> {
        return try {
            val pageIdx = params.key ?: startIdx
            val response = repository.tempGetAbTest(pageIdx)
            val nextKey = if (pageIdx == 1) null else pageIdx - 1
            val prevKey = if (pageIdx == startIdx) null else pageIdx + 1
            LoadResult.Page(
                    data = response,
                    prevKey = prevKey,
                    nextKey = nextKey
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }

    fun setStartIdx(newStartIdx : Int){
        startIdx = newStartIdx
    }
}