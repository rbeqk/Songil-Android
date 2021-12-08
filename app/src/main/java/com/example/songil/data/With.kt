package com.example.songil.data

data class FrontStory(val thumbnail : String, val isLike : Boolean, val likeCount : Int, val title : String, val userName : String)

data class WithQna(val title : String, val content : String, val date : String, val name : String, val isLike : Boolean, val likeCount : Int, val commentCount : Int)