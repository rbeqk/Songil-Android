package com.example.songil.page_article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.SimpleArticle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
    private val repository = ArticleRepository()
    var articleData = MutableLiveData<ArrayList<SimpleArticle>>()

    var articleResultCode = MutableLiveData<Int>()
    var isSocketTimeout = MutableLiveData<Boolean>()

    private val handler = CoroutineExceptionHandler { _, _ ->
        isSocketTimeout.postValue(true)
    }

    fun tryGetArticleData(){
        CoroutineScope(Dispatchers.IO).launch(handler) {
            repository.getArticleTitles().let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.isSuccess){
                        articleData.postValue(response.body()!!.result)
                    }
                    articleResultCode.postValue(response.body()!!.code)
                }
            }
        }
    }
}