package com.example.songil.page_with.with_qna.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.WithQna
import com.example.songil.page_with.with_qna.WithQnaRepository
import java.lang.Exception
import kotlin.reflect.KMutableProperty0

class WithQnaPagingSource(private val repository : WithQnaRepository,
                          private var startIdx : Int, private var isRefresh : KMutableProperty0<Boolean>,
                          private var sort : String) : PagingSource<Int, WithQna>() {
    override fun getRefreshKey(state: PagingState<Int, WithQna>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WithQna> {
        return try {
            val pageIdx : Int
            if (isRefresh.get()){
                pageIdx = startIdx
                isRefresh.set(false)
            } else {
                pageIdx = params.key ?: startIdx
            }
            val response = repository.getQna(pageIdx, sort)
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
}