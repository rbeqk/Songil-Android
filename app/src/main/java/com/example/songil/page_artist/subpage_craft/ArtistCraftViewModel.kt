package com.example.songil.page_artist.subpage_craft

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.Craft1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistCraftViewModel : ViewModel() {
    private val repository = ArtistCraftRepository()
    private var artistIdx = 0
    private var requestPage = 0

    var craftListResult = MutableLiveData<Int>()
    var craftPageCnt = MutableLiveData<Int>()
    var sort = "popular"

    val craftList = ArrayList<Craft1>()
    var newSize = 0

    fun setArtistIdx(idx : Int){
        artistIdx = idx
    }

    // requestPage 가 0 이면, 마지막 페이지까지 요청한 이후 상태
    fun tryGetCraftList(){
        if (requestPage > 0) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.getCraftList(artistIdx, requestPage, sort).let { response ->
                    if (response.isSuccessful) {
                        if (response.body()?.code == 200) {
                            craftList.addAll(response.body()!!.result.craft)
                            newSize = response.body()!!.result.totalCraftCnt
                            requestPage -= 1
                        }
                        craftListResult.postValue(response.body()?.code ?: -1)
                    } else {
                        craftListResult.postValue(response.body()?.code ?: -1)
                    }
                }
            }
        }
    }

    fun tryGetCraftPageCnt(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getCraftPageCnt(artistIdx).let { response ->
                if (response.isSuccessful){
                    if (response.body()?.code == 200){
                        requestPage = response.body()!!.result.totalPages
                        craftPageCnt.postValue(response.body()!!.result.totalPages)
                    } else {
                        craftPageCnt.postValue(0)
                    }
                } else {
                    craftPageCnt.postValue(0)
                }
            }
        }
    }

    fun getCurrentPage() = requestPage
}