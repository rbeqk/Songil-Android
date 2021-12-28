package com.example.songil.page_artist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.ArtistInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistViewModel : ViewModel() {
    private val repository = ArtistRepository()
    var btnCraftActivate = MutableLiveData(true)
    var btnArticleActivate = MutableLiveData(false)
    private var artistIdx = 0
    lateinit var artistInfo : ArtistInfo

    var craftSort = "popular"
    var articleSort = "popular"

    var artistInfoResult = MutableLiveData<Int>()
    var craftCntResult = MutableLiveData<Int>()

    fun setBtnActivate(idx : Int){
        if (idx == 0){
            btnCraftActivate.value = true
            btnArticleActivate.value = false
        } else {
            btnArticleActivate.value = true
            btnCraftActivate.value = false
        }
    }

    fun tryGetArtistInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getArtistInfo(artistIdx).let { response ->
                if (response.isSuccessful){
                    if (response.body()?.code == 200){
                        artistInfo = response.body()!!.result
                    }
                    artistInfoResult.postValue(response.body()?.code ?: -1)
                } else {
                    artistInfoResult.postValue(response.body()?.code ?: -1)
                }
            }
        }
    }

    fun setArtistIdx(idx: Int){
        artistIdx = idx
    }
}