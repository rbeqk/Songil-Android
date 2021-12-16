package com.example.songil.page_home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.ClickData
import com.example.songil.data.ProductSimpleInfo
import com.example.songil.data.SimpleArticle
import com.example.songil.data.TalkWith

class HomeViewModel : ViewModel() {
    var articleData = MutableLiveData<ArrayList<SimpleArticle>>()
    var trendCraftData = MutableLiveData<ArrayList<ProductSimpleInfo>>()
    var recommendCraftData = MutableLiveData<ArrayList<ProductSimpleInfo>>()
    var hotStoryData = MutableLiveData<ArrayList<ClickData>>()
    var talkWithData = MutableLiveData<ArrayList<TalkWith>>()

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
        val fromNetwork = ArrayList<ProductSimpleInfo>()
        fromNetwork.add(ProductSimpleInfo("화병", "브런치", 38000, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg"))
        fromNetwork.add(ProductSimpleInfo("접시", "호미", 38000, "https://images.homify.com/c_fill,f_auto,q_0,w_740/v1439386593/p/photo/image/494903/IMG_0005_1_COEdit.jpg"))
        fromNetwork.add(ProductSimpleInfo("목공예", "대림", 38000, "https://lh3.googleusercontent.com/proxy/wmpLg0_6tDlhPQzRw5LnsO-iWSQ-7yVw2vyPJPDI6LsRPPUVOHvvKbcLi9X3Q3w2gVu1C7dzEu3OaG7AOqloBPAIdzfiqcx_r8p1cU8lv4-rsyxB0WiuT1UXseThGsAr2AXkHNRClD4"))
        fromNetwork.add(ProductSimpleInfo("금속", "백일", 38000, "https://cdn.class101.net/images/07064f5a-c599-4c8b-b77a-a2c0857849ef/original"))
        trendCraftData.value = fromNetwork
        recommendCraftData.value = fromNetwork
    }

    private fun getHotStoryData(){
        val hotStory = ArrayList<ClickData>()
        hotStory.add(ClickData(0, "https://cdn.pixabay.com/photo/2021/10/13/07/43/couple-6705694_960_720.jpg"))
        hotStory.add(ClickData(1, "https://cdn.pixabay.com/photo/2021/12/08/05/13/gyeongbok-palace-6854763_960_720.jpg"))
        hotStory.add(ClickData(2, "https://cdn.pixabay.com/photo/2021/11/08/14/17/europe-6779227_960_720.jpg"))
        hotStory.add(ClickData(3, "https://cdn.pixabay.com/photo/2021/11/06/22/05/camels-6774540_960_720.jpg"))
        hotStory.add(ClickData(4, "https://cdn.pixabay.com/photo/2021/12/11/15/06/northern-lights-6862969_960_720.jpg"))
        hotStory.add(ClickData(5, "https://cdn.pixabay.com/photo/2021/05/11/06/22/night-sky-6245049_960_720.jpg"))
        hotStoryData.value = hotStory
    }

    private fun getTalkWithData(){
        val talkWith = ArrayList<TalkWith>()
        talkWith.add(TalkWith("QnA", "집들이 선물로 어떤게 좋을까요?", 0))
        talkWith.add(TalkWith("AB TEST", "조민지 작가", 1))
        talkWith.add(TalkWith("QnA", "이런 공예품은 어떻게 검색해야 하나요??", 2))
        talkWith.add(TalkWith("AB TEST", "프로브 작가", 3))
        talkWith.add(TalkWith("AB TEST", "조민지 작가", 4))
        talkWith.add(TalkWith("QnA", "조명 색상 추천해주세요~", 5))
        talkWith.add(TalkWith("AB TEST", "귣바 :)", 6))
        talkWithData.value = talkWith
    }

    fun callData(){
        getArticleData()
        getCraftData()
        getHotStoryData()
        getTalkWithData()
    }
}