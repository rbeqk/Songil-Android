package com.example.songil.page_notice

import com.example.songil.data.Notice

class NoticeRepository {
    fun tempGetNotice(pageIdx : Int) : ArrayList<Notice>{
        val temp = arrayListOf<Notice>()

        if (pageIdx != 0){
            for (i in 1 until 6){
                temp.add(Notice("${pageIdx}페이지 ${i}번째 공지사항", "공지사항 내용이 들어갑니다.\n공지사항의 길이는 잘 모르겠네요"))
            }
        }

        return temp
    }
}