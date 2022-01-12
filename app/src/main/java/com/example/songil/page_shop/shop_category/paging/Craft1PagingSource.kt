package com.example.songil.page_shop.shop_category.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.songil.data.Craft1
import com.example.songil.page_shop.shop_category.ShopCategoryRepository
import java.lang.Exception
import kotlin.reflect.KMutableProperty0

class Craft1PagingSource(private val repository : ShopCategoryRepository,
                         private var startIdx : KMutableProperty0<Int>,
                         private var isInit : KMutableProperty0<Boolean>,
                         private var category : KMutableProperty0<String>,
                         private var sort : KMutableProperty0<String>)
    : PagingSource<Int, Craft1>(){

    override fun getRefreshKey(state: PagingState<Int, Craft1>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.minus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Craft1> {
        return try {
            val pageIdx : Int
            if (isInit.get()){
                pageIdx = startIdx.get()
                isInit.set(false)
            } else {
                pageIdx = params.key ?: startIdx.get()
            }
            val response = repository.tempPagingDetailData(category.get(), sort.get(), pageIdx)
            val nextKey = if (pageIdx <= 1) null else pageIdx - 1
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