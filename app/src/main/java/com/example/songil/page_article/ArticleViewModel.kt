package com.example.songil.page_article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.data.SimpleArticle
import kotlinx.coroutines.launch

class ArticleViewModel : BaseViewModel() {
    private val repository = ArticleRepository()
    var articleData = MutableLiveData<ArrayList<SimpleArticle>>()

    fun tryGetArticleData(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getArticleTitles()

            if (result.isSuccessful && result.body()?.isSuccess == true){
                articleData.postValue(result.body()!!.result)
            } else {
                throw UnknownError()
            }
        }
    }
}