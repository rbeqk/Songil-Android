package com.example.songil.page_articlecontent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.ArticleContentInfo
import com.example.songil.data.ArticleDetailInfo
import com.example.songil.data.ProductInArticle

class ArticleContentViewModel : ViewModel() {
    var getArticleResult = MutableLiveData<Int>()
    lateinit var articleData : ArticleDetailInfo

    fun tempGetArticle(){
        val content = arrayListOf(ArticleContentInfo(1, 1, "아무거나 넣어봤어요\n\n생각보다 적을게 안떠오르네요\n그거 아세요? 다다음주가 기말고사래요\n\n허허"),
                ArticleContentInfo(2, 2, imageData = "https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/images%2FIMAGE_20210408_050432_.png?alt=media&token=4d49ed5a-d8ae-40f2-9f65-7f9ec4da22fb"),
                ArticleContentInfo(3, 3, productData = ProductInArticle(1, "흔적", "https://cdn.class101.net/images/07064f5a-c599-4c8b-b77a-a2c0857849ef/original", "윤세환", 21000)))
        articleData = ArticleDetailInfo(1, "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", "낭만주의가 낳은 고정 관념", 1,
                "프로브", "2021.11.25. 20:01", content)
        getArticleResult.value = 200
    }
}