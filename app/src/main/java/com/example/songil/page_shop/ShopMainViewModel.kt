package com.example.songil.page_shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.ClickData
import com.example.songil.data.Craft2
import com.example.songil.page_shop.models.TodayArtistsResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopMainViewModel : ViewModel() {
    private val repository = ShopMainRepository()

    var todayArtist = MutableLiveData<TodayArtistsResult>()
    var todayCrafts = MutableLiveData<ArrayList<Craft2>>()
    var newCrafts = MutableLiveData<ArrayList<ClickData>>()
    var bannerData = MutableLiveData<ArrayList<String>>()

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

    private fun tempGetTodayArtists(){
        val artistInfo = TodayArtistsResult("이택", "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg", "금속 공예")
        todayArtist.value = artistInfo
    }

    private fun tempGetNewCrafts(){
        val newCraft = ArrayList<ClickData>()
        newCraft.add(ClickData(1, "https://images.homify.com/c_fill,f_auto,q_0,w_740/v1439386593/p/photo/image/494903/IMG_0005_1_COEdit.jpg"))
        newCraft.add(ClickData(1, "https://cdn.class101.net/images/07064f5a-c599-4c8b-b77a-a2c0857849ef/original"))
        newCraft.add(ClickData(1, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg"))
        newCrafts.value = newCraft
    }

    private fun tempGetTodayCrafts(){
        val todayCraft = ArrayList<Craft2>()
        todayCraft.add(Craft2(1, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", "화병", "손길", 30000, "Y"))
        todayCraft.add(Craft2(1, "https://cdn.class101.net/images/07064f5a-c599-4c8b-b77a-a2c0857849ef/original", "금속", "손길", 45000, "Y"))
        todayCraft.add(Craft2(1, "https://images.homify.com/c_fill,f_auto,q_0,w_740/v1439386593/p/photo/image/494903/IMG_0005_1_COEdit.jpg", "접시", "손길", 55000, "Y"))
        todayCraft.add(Craft2(1, "https://cdn.pixabay.com/photo/2018/03/29/18/50/diy-3273205_960_720.jpg", "손길", "손길", 30000, "Y"))
        todayCrafts.value = todayCraft
    }

    private fun tempGetBannerData(){
        val banner = ArrayList<String>()
        banner.add("https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/TempBanner%2Fbanner.png?alt=media&token=8d606c51-0bff-4b9a-aaa1-7582b963fbbd")
        banner.add("https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/TempBanner%2Fbanner.png?alt=media&token=8d606c51-0bff-4b9a-aaa1-7582b963fbbd")
        bannerData.value = banner
    }

    fun loadData(){
        tempGetTodayArtists()
        tempGetNewCrafts()
        tempGetTodayCrafts()
        tempGetBannerData()
    }
}