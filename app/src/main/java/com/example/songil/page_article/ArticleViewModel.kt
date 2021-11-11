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

    // test function
    fun getArticleData(){
        val fromNetwork = arrayListOf(
            SimpleArticle("https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", "낭만주의가 낳은 고정 관념", "By. 손길", "아티클"),
            SimpleArticle("https://staccatoh.com/wp-content/uploads/2019/12/UT_9963_resize.jpg", "예술가들과 그들의 공간", "By. 손길", "아티클"),
            SimpleArticle("https://www.artinsight.co.kr/data/tmp/1905/98f3d9dec6db4f8bddb1faba8cbd1160_W8nnbqTE8MC5U3HD.jpg", "사람 - 진정한 예술가란", "By. 손길", "인터뷰"),
            SimpleArticle("https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", "낭만주의가 낳은 고정 관념", "By. 손길", "아티클"),
            SimpleArticle("https://staccatoh.com/wp-content/uploads/2019/12/UT_9963_resize.jpg", "예술가들과 그들의 공간", "By. 손길", "아티클"),
            SimpleArticle("https://www.artinsight.co.kr/data/tmp/1905/98f3d9dec6db4f8bddb1faba8cbd1160_W8nnbqTE8MC5U3HD.jpg", "사람 - 진정한 예술가란", "By. 손길", "인터뷰"))
        articleData.value = fromNetwork
    }

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