package com.example.songil.page_shop.shop_category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.songil.config.GlobalApplication
import com.example.songil.data.Craft1
import com.example.songil.data.Craft2
import com.example.songil.page_shop.shop_category.paging.Craft1PagingSource
import kotlinx.coroutines.flow.Flow
/*import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers*/
import kotlinx.coroutines.launch

class ShopCategoryViewModel : ViewModel() {


    // MutableLiveData 은 view 에서 반응할 때 사용,
    // 일반 type 은 pagingSource 에 인자로 전달하기 위해 존재
    var category = MutableLiveData<String>()
    var categoryString = ""

    var startIdx = MutableLiveData<Int>()
    var startIdxInt = 0

    var sort = MutableLiveData("popular")
    var sortString = "popular"

    var products = ArrayList<Craft2>()

    var popularResultCode = MutableLiveData<Int>()

    lateinit var flow : Flow<PagingData<Craft1>>

    var isRefresh = false

    var totalCraftData = ArrayList<Craft1>()

    private val repository = ShopCategoryRepository()

    fun setInit(){
        viewModelScope.launch {
            flow = Pager(PagingConfig(pageSize = 10)){
                Craft1PagingSource(repository, ::startIdxInt, ::isRefresh, ::categoryString, ::sortString)
            }.flow.cachedIn(viewModelScope)
        }
    }

    fun tempGetStartIdx(){
        viewModelScope.launch {
            isRefresh = true
            val newStartIdx = 20
            startIdxInt = newStartIdx
            startIdx.postValue(newStartIdx)
        }
    }

    fun setCategory(changeCategory : String){
        category.value = changeCategory
        categoryString = changeCategory
        totalCraftData.clear()
    }

    fun changeSort(targetSort : String){
        sort.value = targetSort
        sortString = targetSort
        totalCraftData.clear()
    }

    fun tryGetPopular() {
        viewModelScope.launch {
            Log.d("shop", "request popular ${GlobalApplication.sort[sort.value!!]}, ${category.value!!}")
        }
    }

}