package com.songil.songil.page_home.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.CraftSimpleInfo
import com.songil.songil.data.TalkWith
import com.google.gson.annotations.SerializedName

data class ResponseGetHome(val result : HomeData) : BaseResponse()

data class HomeData(val article : ArrayList<HomeArticle>, val trendCraft: ArrayList<CraftSimpleInfo>, val recommend : ArrayList<CraftSimpleInfo>,
                    val talkWith : ArrayList<TalkWith>, val hotStory : ArrayList<HomeHotStory>)

data class HomeArticle(val articleIdx : Int, val categoryIdx : Int, val title : String, val summary : String, val mainImageUrl : String)

data class HomeHotStory(@SerializedName("storyIdx") val Idx : Int, val mainImageUrl : String)