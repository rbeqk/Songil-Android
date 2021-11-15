package com.example.songil.page_home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.CraftSimpleInfo
import com.example.songil.data.SimpleArticle

class HomeViewModel : ViewModel() {
    var articleData = MutableLiveData<ArrayList<SimpleArticle>>()
    var trendCraftData = MutableLiveData<ArrayList<CraftSimpleInfo>>()
    var recommendCraftData = MutableLiveData<ArrayList<CraftSimpleInfo>>()

    private fun getArticleData(){
        val fromNetwork = arrayListOf(
                SimpleArticle("https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", "낭만주의가 낳은 고정 관념", "By. 손길", "아티클"),
                SimpleArticle("https://staccatoh.com/wp-content/uploads/2019/12/UT_9963_resize.jpg", "예술가들과 그들의 공간", "By. 손길", "아티클"),
                SimpleArticle("https://www.artinsight.co.kr/data/tmp/1905/98f3d9dec6db4f8bddb1faba8cbd1160_W8nnbqTE8MC5U3HD.jpg", "사람 - 진정한 예술가란", "By. 손길", "인터뷰"),
                SimpleArticle("https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", "낭만주의가 낳은 고정 관념", "By. 손길", "아티클"),
                SimpleArticle("https://staccatoh.com/wp-content/uploads/2019/12/UT_9963_resize.jpg", "예술가들과 그들의 공간", "By. 손길", "아티클"),
                SimpleArticle("https://www.artinsight.co.kr/data/tmp/1905/98f3d9dec6db4f8bddb1faba8cbd1160_W8nnbqTE8MC5U3HD.jpg", "사람 - 진정한 예술가란", "By. 손길", "인터뷰"))
        articleData.value = fromNetwork
    }

    private fun getCraftData(){
        val fromNetwork = ArrayList<CraftSimpleInfo>()
        fromNetwork.add(CraftSimpleInfo("화병", "브런치", 38000, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg"))
        fromNetwork.add(CraftSimpleInfo("접시", "호미", 38000, "https://images.homify.com/c_fill,f_auto,q_0,w_740/v1439386593/p/photo/image/494903/IMG_0005_1_COEdit.jpg"))
        fromNetwork.add(CraftSimpleInfo("목공예", "대림", 38000, "https://lh3.googleusercontent.com/proxy/wmpLg0_6tDlhPQzRw5LnsO-iWSQ-7yVw2vyPJPDI6LsRPPUVOHvvKbcLi9X3Q3w2gVu1C7dzEu3OaG7AOqloBPAIdzfiqcx_r8p1cU8lv4-rsyxB0WiuT1UXseThGsAr2AXkHNRClD4"))
        fromNetwork.add(CraftSimpleInfo("금속", "백일", 38000, "https://cdn.class101.net/images/07064f5a-c599-4c8b-b77a-a2c0857849ef/original"))
        trendCraftData.value = fromNetwork
        recommendCraftData.value = fromNetwork
    }

    fun callData(){
        getArticleData()
        getCraftData()
    }
}