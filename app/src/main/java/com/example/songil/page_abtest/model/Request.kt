package com.example.songil.page_abtest.model

// page_story.subpage_story_chat.request 에 있는 것과 동일, 테스트 후 통합 예정
data class RequestWriteComment(val parentIdx : Int?, val comment : String)

data class RequestVote(val vote : String)