package com.example.songil.data

data class FrontStory(val mainImageUrl : String, val isLike : String, val totalLikeCnt : Int, val title : String, val userName : String, val userIdx : Int, val storyIdx : Int)

data class WithQna(val qnaIdx : Int, val userIdx : Int, val userProfile : String?, val userName : String, val title : String, val content : String, val createdAt : String, val isUserQnA : String, val totalLikeCnt : Int, val isLike : String, val totalCommentCnt : Int)

data class TalkWith(val category : String, val title : String, val idx : Int) // home 화면에서 talk with +

data class ABTest(val abTestIdx : Int, val artistIdx : Int, val artistImageUrl : String?, val artistName : String,
                  val content : String, val imageA : String, val imageB : String, val deadline : String, val totalCommentCnt : Int,
                  var isFinished  : String, val voteInfo : ABVoteInfo?, val finishInfo : ABVoteInfo?)

data class ABVoteInfo(val voteImage : String, val totalVoteCnt : Int, val percent : Int)

data class ABTestViewInfo(val abTest : ABTest, var choice : String? = null) // choice 는 투표 전 클릭한 상태를 표현하기 위해 사용되는 변수

data class HotTalk(val categoryIdx : Int, val idx : Int, val text : String, val totalCommentCnt : Int?, val imageUrl : String?)

data class WithNotice(val noticeIdx : Int, val userName : String, val notice : String, val deadline : String)

data class WithStory(val storyIdx : Int, val imageUrl : ArrayList<String>, val title : String, val content : String, val userIdx : Int, val userName : String, val userProfile : String?,
                     val createdAt : String, var totalLikeCount : Int, var isLike : String, val totalCommentCnt : Int, val tag : ArrayList<String>)