package com.example.songil.page_with.with_story.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.FrontStory
import com.example.songil.page_with.with_story.WithStoryRepository
import java.lang.Exception

class WithStoryPagingSource(private val storyRepository : WithStoryRepository, private var startIdx : Int) : PagingSource<Int, FrontStory>(){

    override fun getRefreshKey(state: PagingState<Int, FrontStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FrontStory> {
        return try {
            val pageIdx = params.key ?: startIdx
            val response = storyRepository.tempGetStories(pageIdx)
            val nextKey = if(pageIdx == 1) null else pageIdx - 1
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