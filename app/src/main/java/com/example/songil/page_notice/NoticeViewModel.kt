package com.example.songil.page_notice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.WithNotice

class NoticeViewModel : ViewModel() {
    var noticeList = MutableLiveData<ArrayList<WithNotice>>()

    fun tryGetNotice(){
        val tempNotice = ArrayList<WithNotice>()
        tempNotice.add(WithNotice(1, "프로브", "님이 내 스토리에 좋아요를 눌렀어요", "2021.12.17 23:55"))
        tempNotice.add(WithNotice(2, "제나", "님이 내 QnA에 새로운 의견를 달았어요", "2021.12.17 23:55"))
        tempNotice.add(WithNotice(3, "베리", "님이 내 스토리에 새로운 댓글을 달았어요", "2021.12.17 23:55"))
        tempNotice.add(WithNotice(4, "루스", "님이 내 댓글에 답글를 달았어요", "2021.12.17 23:55"))
        tempNotice.add(WithNotice(5, "제이엠", "님이 내 QnA에 새로운 의견를 달았어요", "2021.12.17 23:55"))
        noticeList.value = tempNotice
    }
}