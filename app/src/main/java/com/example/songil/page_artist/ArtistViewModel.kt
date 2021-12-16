package com.example.songil.page_artist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArtistViewModel : ViewModel() {
    var btnCraftActivate = MutableLiveData(true)
    var btnArticleActivate = MutableLiveData(false)

    fun setBtnActivate(idx : Int){
        if (idx == 0){
            btnCraftActivate.value = true
            btnArticleActivate.value = false
        } else {
            btnArticleActivate.value = true
            btnCraftActivate.value = false
        }
    }
}