package com.example.songil.page_home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.data.ClickData
import com.example.songil.data.CraftSimpleInfo
import com.example.songil.data.TalkWith
import com.example.songil.page_home.models.HomeArticle
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val repository = HomeRepository()
    var articleData = MutableLiveData<ArrayList<HomeArticle>>()
    var trendCraftData = MutableLiveData<ArrayList<CraftSimpleInfo>>()
    var recommendCraftData = MutableLiveData<ArrayList<CraftSimpleInfo>>()
    var hotStoryData = MutableLiveData<ArrayList<ClickData>>()
    var talkWithData = MutableLiveData<ArrayList<TalkWith>>()

    fun tryGetHomeData(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.getHomeData()
            val clickData = ArrayList<ClickData>()
            for (data in result.body()!!.result.hotStory){
                clickData.add(ClickData(data.Idx, data.mainImageUrl))
            }
            hotStoryData.postValue(clickData)
            articleData.postValue(result.body()!!.result.article)
            trendCraftData.postValue(result.body()!!.result.trendCraft)
            recommendCraftData.postValue(result.body()!!.result.recommend)
            talkWithData.postValue(result.body()!!.result.talkWith)
        }
    }

    /*private fun getArticleData(){
        val fromNetwork = arrayListOf(
                HomeArticle(articleIdx = 1, categoryIdx = 1, mainImageUrl = "https://pds.joins.com/news/component/joongang_sunday/2010/09/19004519.jpg", title =  "낭만주의가 낳은 고정 관념",summary =  "By. 손길"),
                HomeArticle(articleIdx = 2, categoryIdx = 3, mainImageUrl = "https://staccatoh.com/wp-content/uploads/2019/12/UT_9963_resize.jpg", title =  "예술가들과 그들의 공간",summary =  "By. 손길"),
                HomeArticle(articleIdx = 3, categoryIdx = 1, mainImageUrl = "https://www.artinsight.co.kr/data/tmp/1905/98f3d9dec6db4f8bddb1faba8cbd1160_W8nnbqTE8MC5U3HD.jpg", title =  "사람 - 진정한 예술가란",summary =  "By. 손길"))
        articleData.value = fromNetwork
    }

    private fun getCraftData(){
        val fromNetwork = ArrayList<CraftSimpleInfo>()
        fromNetwork.add(CraftSimpleInfo(name = "화병", artistName = "브런치", price = 38000, mainImageUrl = "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", artistIdx = 1))
        fromNetwork.add(CraftSimpleInfo(name ="접시", artistName = "호미", price =38000, mainImageUrl = "https://images.homify.com/c_fill,f_auto,q_0,w_740/v1439386593/p/photo/image/494903/IMG_0005_1_COEdit.jpg", artistIdx = 1))
        fromNetwork.add(CraftSimpleInfo(name ="목공예", artistName = "대림", price =38000, mainImageUrl = "https://lh3.googleusercontent.com/proxy/wmpLg0_6tDlhPQzRw5LnsO-iWSQ-7yVw2vyPJPDI6LsRPPUVOHvvKbcLi9X3Q3w2gVu1C7dzEu3OaG7AOqloBPAIdzfiqcx_r8p1cU8lv4-rsyxB0WiuT1UXseThGsAr2AXkHNRClD4", artistIdx = 1))
        fromNetwork.add(CraftSimpleInfo(name ="금속", artistName = "백일", price =38000, mainImageUrl = "https://cdn.class101.net/images/07064f5a-c599-4c8b-b77a-a2c0857849ef/original", artistIdx = 1))
        trendCraftData.value = fromNetwork
        recommendCraftData.value = fromNetwork
    }

    private fun getHotStoryData(){
        val hotStory = ArrayList<ClickData>()
        hotStory.add(ClickData(11, "https://cdn.pixabay.com/photo/2021/10/13/07/43/couple-6705694_960_720.jpg"))
        hotStory.add(ClickData(1, "https://cdn.pixabay.com/photo/2021/12/08/05/13/gyeongbok-palace-6854763_960_720.jpg"))
        hotStory.add(ClickData(2, "https://cdn.pixabay.com/photo/2021/11/08/14/17/europe-6779227_960_720.jpg"))
        hotStory.add(ClickData(3, "https://cdn.pixabay.com/photo/2021/11/06/22/05/camels-6774540_960_720.jpg"))
        hotStory.add(ClickData(12, "https://cdn.pixabay.com/photo/2021/12/11/15/06/northern-lights-6862969_960_720.jpg"))
        hotStory.add(ClickData(14, "https://cdn.pixabay.com/photo/2021/05/11/06/22/night-sky-6245049_960_720.jpg"))
        hotStoryData.value = hotStory
    }

    private fun getTalkWithData(){
        val talkWith = ArrayList<TalkWith>()
        talkWith.add(TalkWith(1, "집들이 선물로 어떤게 좋을까요?", 0))
        talkWith.add(TalkWith(2, "조민지 작가", 1))
        talkWith.add(TalkWith(1, "이런 공예품은 어떻게 검색해야 하나요??", 2))
        talkWith.add(TalkWith(2, "프로브 작가", 3))
        talkWith.add(TalkWith(2, "조민지 작가", 4))
        talkWith.add(TalkWith(1, "조명 색상 추천해주세요~", 5))
        talkWith.add(TalkWith(2, "귣바 :)", 6))
        talkWithData.value = talkWith
    }

    fun callData(){
        getArticleData()
        getCraftData()
        getHotStoryData()
        getTalkWithData()
    }*/
}