package com.example.songil.page_article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.SimpleArticle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
    private val repository = ArticleRepository()
    var articleData = MutableLiveData<ArrayList<SimpleArticle>>()

    var articleResultCode = MutableLiveData<Int>()

    fun tryGetArticleData(){
        CoroutineScope(Dispatchers.IO).launch {
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