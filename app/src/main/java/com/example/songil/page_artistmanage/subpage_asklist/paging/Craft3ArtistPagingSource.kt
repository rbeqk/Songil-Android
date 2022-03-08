package com.example.songil.page_artistmanage.subpage_asklist.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.Ask
import com.example.songil.page_artistmanage.subpage_asklist.ArtistManageAskListRepository
import java.lang.Exception

class Craft3ArtistPagingSource(private val repository : ArtistManageAskListRepository, private var startIdx : Int) : PagingSource<Int, Ask>() {
    override fun getRefreshKey(state: PagingState<Int, Ask>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Ask> {
        return try {
            val pageIdx = params.key ?: startIdx
            val response = repository.getAskList(pageIdx)
            val nextKey = if (pageIdx == 1) null else pageIdx - 1
            val prevKey = if (pageIdx == startIdx) null else pageIdx + 1
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