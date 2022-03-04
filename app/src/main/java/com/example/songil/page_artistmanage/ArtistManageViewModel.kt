package com.example.songil.page_artistmanage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.page_artistmanage.models.ArtistPageInfo
import kotlinx.coroutines.launch

class ArtistManageViewModel : BaseViewModel() {
    private val repository = ArtistManageRepository()

    private val _getArtistPageInfoResult = MutableLiveData<Int>()
    val getArtistPageInfoResult : LiveData<Int> get() = _getArtistPageInfoResult

    lateinit var artistPageInfo : ArtistPageInfo

    fun tryGetArtistPageInfo(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getArtistPageInfo()
            artistPageInfo = result.result
            _getArtistPageInfoResult.postValue(result.code)
        }
    }
}