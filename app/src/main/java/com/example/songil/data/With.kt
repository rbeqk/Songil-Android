package com.example.songil.data

data class FrontStory(val thumbnail : String, val isLike : Boolean, val likeCount : Int, val title : String, val userName : String)

data class WithQna(val title : String, val content : String, val date : String, val name : String, val isLike : Boolean, val likeCount : Int, val commentCount : Int)

data class TalkWith(val category : String, val title : String, val idx : Int)

data class ABTest(val abTestIdx : Int, val artistIdx : Int, val artistImageUrl : String?, val artistName : String,
                  val content : String, val imageA : String, val imageB : String, val deadline : String, val totalCommentCnt : Int,
                  var isFinished  : String, val voteInfo : ABVoteInfo?, val finishInfo : ABVoteInfo?)

data class ABVoteInfo(val voteImage : String, val totalVoteCnt : Int, val percent : Int)

data class ABTestViewInfo(val abTest : ABTest, var choice : String? = null)