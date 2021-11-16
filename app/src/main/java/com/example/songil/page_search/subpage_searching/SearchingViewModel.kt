package com.example.songil.page_search.subpage_searching

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchingViewModel : ViewModel() {
    var changeRecentSearch = MutableLiveData<Int>() // 최근 검색어의 경우, 개별삭제 기능이 있기에 이를 고려해 list 를 mutableLiveData 로 넣지 않았다
    var recentSearch = ArrayList<String>()

    var popularSearch = MutableLiveData<ArrayList<String>>()    // 이건 수정할 일이 없어 그냥 liveData 로 사용

    fun tempGetRecent(){
        val tempData = arrayListOf("도자기", "물결화병", "모빌세트")
        recentSearch = tempData
        changeRecentSearch.value = -1
    }

    fun tempGetPopular(){
        val tempData = arrayListOf("물결화병", "모빌세트", "집들이", "조민지", "홈퍼니싱", "도자기", "서울공예박물관")
        popularSearch.value = tempData
    }

    fun removeRecentWord(position : Int){
        if (position in 0 until recentSearch.size){
            recentSearch.removeAt(position)
            changeRecentSearch.value = position
        } else {
            Log.d("removeRecentWord's warning", "$position is out of Range")
        }
    }
}