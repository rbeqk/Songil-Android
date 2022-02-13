package com.example.songil.data

data class FrontStory(val mainImageUrl : String, val isLike : String, val totalLikeCnt : Int, val title : String, val userName : String, val userIdx : Int, val storyIdx : Int)

data class WithQna(val qnaIdx : Int, val userIdx : Int, val userProfile : String?, val userName : String, val title : String, val content : String,
                   val createdAt : String, val isUserQnA : String, var totalLikeCnt : Int, var isLike : String, var totalCommentCnt : Int) : HeaderPost()

data class TalkWith(val categoryIdx : Int, val text : String, val idx : Int) // home 화면에서 talk with +

data class ABTest(val abTestIdx : Int = -1, val artistIdx : Int = -1, val artistImageUrl : String? = null, val artistName : String = "",
                  val content : String = "", val imageA : String = "", val imageB : String = "", val deadline : String = "", val totalCommentCnt : Int = -1,
                  var isFinished : String = "", val isUserABTest : String = "", val voteInfo : ABVoteInfo? = null, val finalInfo : ABVoteInfo? = null) : HeaderPost()

data class ABVoteInfo(val vote : String, val totalVoteCnt : Int, val percent : Int)

data class ABTestViewInfo(val abTest : ABTest, var choice : String? = null) // choice 는 투표 전 클릭한 상태를 표현하기 위해 사용되는 변수

data class HotTalk(val categoryIdx : Int, val idx : Int, val text : String, val totalCommentCnt : Int?, val imageUrl : String?)

data class WithNotice(val noticeIdx : Int, val userName : String, val notice : String, val deadline : String)

data class WithStory(val storyIdx : Int, val imageUrl : ArrayList<String>, val title : String, val content : String, val userIdx : Int, val userName : String, val userProfile : String?,
                     val createdAt : String, val isUserStory : String, var totalLikeCnt : Int, var isLike : String, val totalCommentCnt : Int, val tag : ArrayList<String>)

// qna, story 등에서 보이는 댓글들
data class Chat(val commentIdx : Int, val userIdx : Int, val userProfile : String?, val userName : String,
                val isWriter : String, val comment : String, val createdAt : String, val isUserComment : String,
                val isDeleted : String, val isReported : String, val reComment : ArrayList<ChatReply>) : HeaderPost()

data class ChatReply(val commentIdx : Int, val userIdx : Int, val userProfile : String?, val userName : String,
                     val isWriter: String, val comment : String, val createdAt : String, val isUserComment : String)

// post (ab-test, qna) 가 header 이고, chat 이 content 인 postAndChatAdapter 에서 사용하기 위해 만든 클래스
sealed class HeaderPost()

data class Post(val idx : Int, val categoryIdx : Int, val mainImageUrl: String?, val title : String?, val content : String, val name : String, val createdAt: String,
                val totalLikeCnt: Int?, val isLike : String?, val totalCommentCnt: Int)

// 여기 좀 깔끔하게 정리해야 할듯