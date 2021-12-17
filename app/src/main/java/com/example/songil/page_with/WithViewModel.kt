package com.example.songil.page_with

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.HotTalk

class WithViewModel : ViewModel() {
    var loadHotTalkResult = MutableLiveData<Int>()
    var hotTalkData = ArrayList<HotTalk>()

    fun tryGetHotTalk(){
        hotTalkData.add(HotTalk("QnA", 1, "집들이 선물로 어떤게 좋을까요?", 12, null))
        hotTalkData.add(HotTalk("AB TEST", 2, "손길", null, "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg"))
        hotTalkData.add(HotTalk("AB TEST", 3, "윤세환", null, "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg"))
        hotTalkData.add(HotTalk("QnA", 4, "색상 추천 부탁드립니다..!", 9, null))
        hotTalkData.add(HotTalk("AB TEST", 5, "손길", null, "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg"))
        hotTalkData.add(HotTalk("QnA", 6, "이런 공예품은 어떻게 검색해야 나오나요??", 20, null))

        loadHotTalkResult.value = 200
    }
}