package com.songil.songil.data

data class Ask(val askIdx : Int, val craftIdx : Int, val mainImageUrl : String, val name : String, val nickname : String, val createdAt : String, var status : Int)

data class AskDetail(val askIdx : Int, val craftIdx : Int, val name : String, val userIdx : Int, val nickname : String, val askContent : String, val answerContent : String?)