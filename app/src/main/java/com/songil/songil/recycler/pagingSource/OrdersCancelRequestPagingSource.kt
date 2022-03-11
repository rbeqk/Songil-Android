package com.songil.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.songil.songil.page_artistmanage.subpage_cancel_request.ArtistManageCancelRequestRepository
import com.songil.songil.page_artistmanage.subpage_cancel_request.models.GetCancelRequestItemResponseBody

class OrdersCancelRequestPagingSource(private val repository : ArtistManageCancelRequestRepository, private val startIdx : Int) : PagingSource<Int, GetCancelRequestItemResponseBody>(){
    override fun getRefreshKey(state: PagingState<Int, GetCancelRequestItemResponseBody>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetCancelRequestItemResponseBody> {
        return try {
            val pageIdx = params.key ?: startIdx
            val response = repository.getCancelRequestItemList(pageIdx).result
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