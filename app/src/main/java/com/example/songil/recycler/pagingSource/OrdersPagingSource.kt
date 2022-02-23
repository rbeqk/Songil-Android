package com.example.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.page_orderstatus.OrderStatusRepository
import com.example.songil.page_orderstatus.models.OrderStatusInfo
import java.lang.Exception

class OrdersPagingSource(private val repository : OrderStatusRepository) : PagingSource<Int, OrderStatusInfo>(){
    override fun getRefreshKey(state: PagingState<Int, OrderStatusInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderStatusInfo> {
        return try {
            val pageIdx = params.key ?: 1
            val response = repository.getOrderStatus(pageIdx)
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