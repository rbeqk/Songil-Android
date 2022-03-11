package com.songil.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.songil.songil.page_artistmanage.subpage_orderstat.ArtistManageOrderStatRepository
import com.songil.songil.page_artistmanage.subpage_orderstat.models.GetArtistOrderStatusResponseBody
import java.lang.Exception

class OrdersArtistPagingSource(private val repository : ArtistManageOrderStatRepository, private val startIdx : Int) : PagingSource<Int, GetArtistOrderStatusResponseBody>(){
    override fun getRefreshKey(state: PagingState<Int, GetArtistOrderStatusResponseBody>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetArtistOrderStatusResponseBody> {
        return try {
            val pageIdx = params.key ?: startIdx
            val response = repository.getArtistOrderStat(pageIdx).result
            val prevKey = if (pageIdx == startIdx) null else pageIdx + 1
            val nextKey = if (pageIdx == 1) null else pageIdx - 1
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