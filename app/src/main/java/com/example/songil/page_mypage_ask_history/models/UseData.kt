package com.example.songil.page_mypage_ask_history.models

data class MyPageAskTotalData(val askIdx : Int, val ask : MyPageAsk, val answer : MyPageAnswer?)

data class MyPageAsk(val craftIdx : Int, val name : String, val content : String, val createdAt : String, val status : Int)

data class MyPageAnswer(val artistIdx : Int, val artistName : String, val content : String, val createdAt: String)