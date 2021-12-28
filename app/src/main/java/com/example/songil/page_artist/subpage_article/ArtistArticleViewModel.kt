package com.example.songil.page_artist.subpage_article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.ItemArticle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistArticleViewModel : ViewModel() {
    private val repository = ArtistArticleRepository()
    private var artistIdx = 0
    private var requestPage = 0

    var articleListResult = MutableLiveData<Int>()
    var articlePageCnt = MutableLiveData<Int>()
    var sort = "popular"

    var articleList = ArrayList<ItemArticle>()
    var newSize = 0

    fun setArtistIdx(idx : Int){
        artistIdx = idx
    }

    fun tryGetArticleList(){
        if (requestPage > 0){
            CoroutineScope(Dispatchers.IO).launch {
                repository.getArticleList(artistIdx, requestPage, sort).let { response ->
                    if (response.isSuccessful){
                        if (response.body()?.code == 200){
                            articleList.addAll(response.body()!!.result.article)
                            newSize = response.body()!!.result.totalArticleCnt
                            requestPage -= 1
                        }
                        articleListResult.postValue(response.body()?.code ?: -1)
                    } else {
                        articleListResult.postValue(response.body()?.code ?: -1)
                    }
                }
            }
        }
    }

    fun tryGetArticlePageCnt(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getArtistPageCnt(artistIdx).let { response ->
                if (response.isSuccessful){
                    if (response.body()?.code == 200){
                        requestPage = response.body()!!.result.totalPages
                        articlePageCnt.postValue(requestPage)
                    } else {
                        articlePageCnt.postValue(0)
                    }
                } else {
                    articlePageCnt.postValue(0)
                }
            }
        }
    }
}