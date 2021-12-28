package com.example.songil.page_shop.shop_category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songil.config.GlobalApplication
import com.example.songil.data.Craft1
import com.example.songil.data.Craft2
/*import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers*/
import kotlinx.coroutines.launch

class ShopCategoryViewModel : ViewModel() {
    var category = MutableLiveData<String>()

    // new
    var startIdx = 0
    var nextPage = 10
    var products = ArrayList<Craft2>()
    var sort = MutableLiveData<String>("popular")
    var popularResultCode = MutableLiveData<Int>()
    var allCraftResultCode = MutableLiveData<Int>()

    var totalCraftData = ArrayList<Craft1>()

    private val repository = ShopCategoryRepository()

    fun setCategory(changeCategory : String){
        category.value = changeCategory
        totalCraftData.clear()
    }

    fun changeSort(targetSort : String){
        sort.value = targetSort
        totalCraftData.clear()
        //normalSort = targetSort
    }

    // new
    fun tryGetPopular() {
        viewModelScope.launch {
            Log.d("shop", "request popular ${GlobalApplication.sort[sort.value!!]}, ${category.value!!}")
        }
    }

    // new
    fun tryGetProduct(){
        viewModelScope.launch {
            //Log.d("shop", "request products ${GlobalApplication.sort[sort.value!!]}, ${category.value!!}")
            if (nextPage >= 1) {
                val temp = repository.tempPagingDetailData(category.value!!, sort.value!!, nextPage)
                totalCraftData.addAll(temp)
                nextPage -= 1
                allCraftResultCode.value = 200
            }
        }
    }

}