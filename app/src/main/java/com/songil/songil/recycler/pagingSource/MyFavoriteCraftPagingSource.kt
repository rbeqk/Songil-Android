package com.songil.songil.recycler.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.songil.songil.data.Craft1
import com.songil.songil.page_myfavorite_craft.MyFavoriteCraftRepository

class MyFavoriteCraftPagingSource(private val repository : MyFavoriteCraftRepository) : PagingSource<Int, Craft1>(){
    override fun getRefreshKey(state: PagingState<Int, Craft1>): Int? {
        return state.anchorPosition/*?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }*/
        // 일단 여기서는 closePageToPosition 미사용 시도
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Craft1> {
        return try {
            val pageIdx = params.key ?: 1
            val response = repository.getMyFavoriteCraft(pageIdx).body()!!.result
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