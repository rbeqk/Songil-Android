package com.example.songil.page_mycomment.models

data class ResponseBodyGetWritableComment(val orderDetailIdx : Int, val craftIdx : Int, val name : String, val mainImageUrl : String, val artistIdx : Int, val artistName : String)

data class ResponseBodyGetWrittenComment(val commentIdx : Int, val craftIdx : Int, val name : String, val artistIdx : Int, val artistName : String,
                                         val createdAt : String, val imageUrl : ArrayList<String>, val content : String)