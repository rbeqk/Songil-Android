package com.songil.songil.page_with.with_story.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.songil.songil.data.FrontStory
import com.songil.songil.page_with.with_story.WithStoryRepository
import java.lang.Exception
import kotlin.reflect.KMutableProperty0

class WithStoryPagingSource(private val storyRepository : WithStoryRepository,
                            private var startIdx : Int, private var isRefresh : KMutableProperty0<Boolean>,
                            private var sort : String) : PagingSource<Int, FrontStory>(){

    override fun getRefreshKey(state: PagingState<Int, FrontStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FrontStory> {
        return try {
            val pageIdx : Int
            if (isRefresh.get()){
                pageIdx = startIdx
                isRefresh.set(false)
            } else {
                pageIdx = params.key ?: startIdx
            }
            val response = storyRepository.getStory(pageIdx, sort)
            val nextKey = if(pageIdx <= 1) null else pageIdx - 1
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