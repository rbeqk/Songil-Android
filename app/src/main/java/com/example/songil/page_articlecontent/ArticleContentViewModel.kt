package com.example.songil.page_articlecontent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.ArticleDetailInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleContentViewModel : ViewModel() {
    private val repository = ArticleContentRepository()

    var getArticleResult = MutableLiveData<Int>()
    var favButtonResultCode = MutableLiveData<Int>()
    lateinit var articleData : ArticleDetailInfo

    fun tryGetArticleContent(articleIdx : Int){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getArticleContent(articleIdx).let { response ->
                if (response.isSuccessful){
                    when (response.body()!!.code){
                        200 -> {
                            articleData = response.body()!!.result
                        }
                    }
                    getArticleResult.postValue(response.body()!!.code)
                } else {
                    getArticleResult.postValue(-1)
                }
            }
        }
    }

    fun tryChangeLikeData(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.patchArticleLike(articleData.articleIdx).let { response ->
                if (response.isSuccessful){
                    when (response.body()!!.code){
                        200 -> {
                            val likeData = response.body()!!.result
                            articleData.isLike = likeData.isLike
                            articleData.totalLikeCnt = likeData.totalLikeCnt
                        }
                    }
                    favButtonResultCode.postValue(response.body()!!.code)
                } else {
                    favButtonResultCode.postValue(-1)
                }
            }
        }
    }

    fun shareMessage() : String {
        return "[songil]\n${articleData.editorName}의 ${articleData.title}\nsongile앱에서 \"${articleData.editorName}\" 또는 \"${articleData.title}\"로 검색시 찾으실 수 있습니다"
    }
}