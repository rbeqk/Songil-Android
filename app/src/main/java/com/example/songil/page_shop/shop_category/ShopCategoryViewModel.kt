package com.example.songil.page_shop.shop_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.page_shop.shop_category.models.CraftDetail
import com.example.songil.page_shop.shop_category.models.CraftSimple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopCategoryViewModel : ViewModel() {
    var category = MutableLiveData<String>()
    var popularCrafts = MutableLiveData<ArrayList<CraftSimple>>()
    var normalCrafts = MutableLiveData<ArrayList<CraftDetail>>()
    private val repository = ShopCategoryRepository()

    fun setCategory(changeCategory : String){
        category.value = changeCategory
    }

    fun requestProductAll(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getProductAll(category.value!!).let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1000){
                        popularCrafts.postValue(response.body()!!.result.popularList)
                        normalCrafts.postValue(response.body()!!.result.productsList)
                    }
                }
            }
        }
    }
}