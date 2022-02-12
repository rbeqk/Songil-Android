package com.example.songil.page_shop.shop_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.songil.config.BaseViewModel
import com.example.songil.config.GlobalApplication
import com.example.songil.data.Craft1
import com.example.songil.data.Craft2
import com.example.songil.page_shop.shop_category.paging.Craft1PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ShopCategoryViewModel : BaseViewModel() {

    var category = MutableLiveData<String>()
    var categoryIdx = 0

    var startIdx = MutableLiveData<Int>()
    var startIdxInt = 0

    var sort = MutableLiveData("popular")
    var sortString = "popular"

    var popularCrafts = ArrayList<Craft2>()
    var popularResultCode = MutableLiveData<Int>()

    lateinit var flow : Flow<PagingData<Craft1>>

    var isRefresh = false

    private val repository = ShopCategoryRepository()

    fun setInit(){
        flow = Pager(PagingConfig(pageSize = 10)){
            Craft1PagingSource(repository, ::startIdxInt, ::isRefresh, ::categoryIdx, ::sortString)
        }.flow
    }

    fun tryGetStartIdx(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getStartIdx(categoryIdx)
            if (result.body()?.code == 200){
                isRefresh = true
                startIdxInt = result.body()!!.result.totalPages
                startIdx.postValue(startIdxInt)
            }
        }
    }

    fun setCategory(changeCategory : Int){
        category.value = GlobalApplication.categoryList[changeCategory - 1].category
        categoryIdx = changeCategory
    }

    fun changeSort(targetSort : String){
        sort.value = targetSort
        sortString = targetSort
    }

    fun tryGetPopular() {
        viewModelScope.launch(exceptionHandler) {
            viewModelScope.launch(exceptionHandler) {
                val result = repository.getPopularCraft(categoryIdx)
                if (result.body()?.code == 200){
                    popularCrafts = result.body()!!.result
                }
                popularResultCode.postValue(result.body()?.code ?: -1)
            }
        }
    }

}