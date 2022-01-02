package com.example.songil.page_notice.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.Notice
import com.example.songil.page_notice.NoticeRepository
import java.lang.Exception

class NoticePagingSource(private val noticeRepository : NoticeRepository, private var startIdx : Int) : PagingSource<Int, Notice>() {
    override fun getRefreshKey(state: PagingState<Int, Notice>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notice> {
        return try {
            val pageIdx = params.key ?: startIdx
            val response = noticeRepository.tempGetNotice(pageIdx)
            val nextKey = if (pageIdx == 1) null else pageIdx - 1
            val prevKey = if (pageIdx == startIdx) null else pageIdx + 1
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }

    fun setStartIdx(newStartIdx : Int){
        startIdx = newStartIdx
    }
}