package com.songil.songil.page_shop.shop_category.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.songil.songil.data.Craft1
import com.songil.songil.page_shop.shop_category.ShopCategoryRepository
import java.lang.Exception
import kotlin.reflect.KMutableProperty0

class ShopCategoryCraftsPagingSource(private val repository : ShopCategoryRepository,
                                     private var startIdx : KMutableProperty0<Int>,
                                     private var isInit : KMutableProperty0<Boolean>,
                                     private var category : KMutableProperty0<Int>,
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
            val response = repository.tryGetProductAll(category = category.get(), filter = sort.get(), page = pageIdx)//tempPagingDetailData(category.get(), sort.get(), pageIdx)
            val nextKey = if (pageIdx <= 1) null else pageIdx - 1
            val prevKey = if (pageIdx == startIdx.get()) null else pageIdx + 1
            LoadResult.Page(
                    data = response.body()!!.result.crafts,
                    prevKey = prevKey,
                    nextKey = nextKey
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}