package com.example.songil.data

data class FrontStory(val thumbnail : String, val isLike : Boolean, val likeCount : Int, val title : String, val userName : String)

data class WithQna(val title : String, val content : String, val date : String, val name : String, val isLike : Boolean, val likeCount : Int, val commentCount : Int)

data class TalkWith(val category : String, val title : String, val idx : Int) // home 화면에서 talk with +

data class ABTest(val abTestIdx : Int, val artistIdx : Int, val artistImageUrl : String?, val artistName : String,
                  val content : String, val imageA : String, val imageB : String, val deadline : String, val totalCommentCnt : Int,
                  var isFinished  : String, val voteInfo : ABVoteInfo?, val finishInfo : ABVoteInfo?)

data class ABVoteInfo(val voteImage : String, val totalVoteCnt : Int, val percent : Int)

data class ABTestViewInfo(val abTest : ABTest, var choice : String? = null) // choice 는 투표 전 클릭한 상태를 표현하기 위해 사용되는 변수

data class HotTalk(val category : String, val idx : Int, val title : String, val commentCnt : Int?, val artistImageUrl : String?)

data class WithNotice(val noticeIdx : Int, val userName : String, val notice : String, val deadline : String)