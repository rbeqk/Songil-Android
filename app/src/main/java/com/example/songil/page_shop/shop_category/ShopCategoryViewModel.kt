package com.example.songil.page_shop.shop_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShopCategoryViewModel : ViewModel() {
    var category = MutableLiveData<String>()

    fun setCategory(changeCategory : String){
        category.value = changeCategory
    }

}