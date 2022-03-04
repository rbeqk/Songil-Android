package com.example.songil.data

data class SongilUserInfo(val userIdx : Int = -1, val userName : String = "", val userProfile : String? = null,
                          val point : Int = 0, val benefitCnt : Int = 0, val likedCraftCnt : Int = 0, val orderCnt : Int = 0,
                          val commentCnt : Int = 0, val askCnt : Int = 0)


// 리팩토링 이후 사용할 UserInfo
data class UserSimple(val userIdx : Int = -1, val userName : String = "", val userProfile : String? = null, val userIntro : String = "")