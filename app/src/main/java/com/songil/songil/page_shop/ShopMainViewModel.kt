package com.songil.songil.page_shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.data.ClickData
import com.songil.songil.data.Craft2
import com.songil.songil.page_shop.models.ShopMainBanner
import com.songil.songil.page_shop.models.TodayArtistsResult
import kotlinx.coroutines.launch

class ShopMainViewModel : BaseViewModel() {
    private val repository = ShopMainRepository()

    var todayArtist = MutableLiveData<TodayArtistsResult>()
    var todayCrafts = MutableLiveData<ArrayList<Craft2>>()
    var newCrafts = MutableLiveData<ArrayList<ClickData>>()
    var bannerData = MutableLiveData<ArrayList<ShopMainBanner>>()

    fun tryGetShopMain(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getShopMainData().body()!!
            val newCraft = ArrayList<ClickData>()
            for (craft in result.result.newCraft){
                newCraft.add(ClickData(craft.craftIdx, craft.mainImageUrl))
            }
            newCrafts.postValue(newCraft)
            bannerData.postValue(result.result.banner)
            todayCrafts.postValue(result.result.todayCraft)
            todayArtist.postValue(result.result.todayArtist)
        }
    }
}