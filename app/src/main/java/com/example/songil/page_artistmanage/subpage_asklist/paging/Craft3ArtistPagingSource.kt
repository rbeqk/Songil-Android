package com.example.songil.page_artistmanage.subpage_asklist.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.Craft3
import com.example.songil.page_artistmanage.subpage_asklist.ArtistManageAskListRepository
import java.lang.Exception
import kotlin.reflect.KMutableProperty0

class Craft3ArtistPagingSource(private val repository : ArtistManageAskListRepository, private var startIdx : KMutableProperty0<Int>, private var isRefresh : KMutableProperty0<Boolean>) : PagingSource<Int, Craft3>() {
    override fun getRefreshKey(state: PagingState<Int, Craft3>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Craft3> {
        return try {
            val pageIdx: Int
            if (isRefresh.get()){
                pageIdx = startIdx.get()
                isRefresh.set(false)
            } else {
                pageIdx = params.key ?: startIdx.get()
            }
            val response = repository.tempGetAskList(pageIdx)
            val nextKey = if (pageIdx == 1) null else pageIdx - 1
            val prevKey = if (pageIdx == startIdx.get()) null else pageIdx + 1
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