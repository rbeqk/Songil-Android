package com.example.songil.page_with.with_story

import com.example.songil.data.FrontStory

class WithStoryRepository {

    fun tempGetStories(pageIdx : Int) : ArrayList<FrontStory>{
        val temp = arrayListOf<FrontStory>()

        for (i in 19 downTo 0){
            val tempStory = FrontStory("https://cdn.class101.net/images/07064f5a-c599-4c8b-b77a-a2c0857849ef/original", true, 200, "${i}번째 스토리입니다.", "${pageIdx}페이지")
            temp.add(tempStory)
        }

        return if (pageIdx == 20){
            val temp2 = arrayListOf<FrontStory>()
            temp2.addAll(temp.subList(10, temp.size - 1))
            temp2
        } else {
            temp
        }
    }
}