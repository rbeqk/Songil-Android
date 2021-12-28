package com.example.songil.data

data class ArtistInfo(val artistIdx : Int, val name : String, val imageUrl : String?, val introduction : String,
                      val company : String, val major : String, val profile : ArrayList<String>, val exhibition : ArrayList<String>,
                      val totalCraftCnt : Int, val totalArticleCnt : Int)