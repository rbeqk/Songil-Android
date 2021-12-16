package com.example.songil.page_shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.Craft2
import com.example.songil.page_shop.models.NewCraft
import com.example.songil.page_shop.models.TodayArtistsResult
import com.example.songil.page_shop.models.TodayCraft
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopMainViewModel : ViewModel() {
    private val repository = ShopMainRepository()

    var todayArtist = MutableLiveData<TodayArtistsResult>()
    var todayCrafts = MutableLiveData<ArrayList<Craft2>>()
    var newCrafts = MutableLiveData<ArrayList<NewCraft>>()

    fun tryGetTodayArtists(){
        CoroutineScope(Dispatchers.IO).launch {
/*            repository.getTodayArtists().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1000){
                        todayArtist.postValue(response.body()!!.result)
                    }
                }
            }*/
        }
    }

    fun tryGetTodayCrafts(){
        CoroutineScope(Dispatchers.IO).launch {
/*            repository.getTodayCrafts().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1000){
                        todayCrafts.postValue(response.body()!!.result)
                    }
                }
            }*/
        }
    }

    fun tryGetNewCrafts(){
        CoroutineScope(Dispatchers.IO).launch {
/*            repository.getNewCrafts().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1000){
                        newCrafts.postValue(response.body()!!.result)
                    }
                }
            }*/
        }
    }
}