package com.example.songil.page_shop.shop_category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songil.config.GlobalApplication
import com.example.songil.data.ProductSimple
/*import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers*/
import kotlinx.coroutines.launch

class ShopCategoryViewModel : ViewModel() {
    var category = MutableLiveData<String>()

    // new
    var startIdx = 0
    var products = ArrayList<ProductSimple>()
    var sort = MutableLiveData<String>("popular")
    var popularResultCode = MutableLiveData<Int>()
    var allCraftResultCode = MutableLiveData<Int>()

    private val repository = ShopCategoryRepository()

    fun setCategory(changeCategory : String){
        category.value = changeCategory
    }

    fun changeSort(targetSort : String){
        sort.value = targetSort
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
            Log.d("shop", "request products ${GlobalApplication.sort[sort.value!!]}, ${category.value!!}")
        }
    }

}