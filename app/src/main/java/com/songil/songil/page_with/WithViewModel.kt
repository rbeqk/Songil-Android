package com.songil.songil.page_with

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.data.HotTalk
import kotlinx.coroutines.launch

class WithViewModel : BaseViewModel() {
    private val repository = WithRepository()
    var loadHotTalkResult = MutableLiveData<Int>()
    var hotTalkData = ArrayList<HotTalk>()

    fun tempTryGetHotTalk(){
        hotTalkData.add(HotTalk(1, 1, "집들이 선물로 어떤게 좋을까요?", 12, null))
        hotTalkData.add(HotTalk(2, 2, "손길", null, "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg"))
        hotTalkData.add(HotTalk(2, 3, "윤세환", null, "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg"))
        hotTalkData.add(HotTalk(1, 4, "색상 추천 부탁드립니다..!", 9, null))
        hotTalkData.add(HotTalk(2, 5, "손길", null, "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg"))
        hotTalkData.add(HotTalk(1, 6, "이런 공예품은 어떻게 검색해야 나오나요??", 20, null))

        loadHotTalkResult.value = 200
    }

    fun tryGetHotTalk() {
        viewModelScope.launch(exceptionHandler) {
            val response = repository.getHotTalk()
            if (response.isSuccessful){
                if (response.body()?.code == 200){
                    hotTalkData = response.body()!!.result
                }
            }
            loadHotTalkResult.postValue(response.body()?.code ?: -1)
        }
    }
}