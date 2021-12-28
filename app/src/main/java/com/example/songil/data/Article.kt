package com.example.songil.data

data class ArticleDetailInfo(val articleCategoryIdx : Int, val articleIdx : Int, val mainImageUrl : String, val title : String, val editorIdx : Int,
                             val editorName : String, val createdAt : String, var isLike : String, var totalLikeCnt : Int, val content : ArrayList<ArticleContentInfo>,
                             val tag : ArrayList<String>, val relatedArticle : ArrayList<SimpleArticle>)

data class ArticleContentInfo(val index : Int, val type : Int, val textData : String? = null, val imageData : String?= null, val craftData : CraftInArticle ?= null)

data class CraftInArticle(val craftIdx : Int, val name : String, val mainImageUrl : String, val artistName : String, val price : Int)

data class SimpleArticle(val articleIdx : Int = 0, val title : String, val mainImageUrl : String, val editorIdx : Int, val editorName : String, val articleCategoryIdx : Int = 4)

data class ItemArticle(val articleIdx : Int, val title : String, val mainImageUrl : String, val editorIdx : Int, val editorName : String, val createdAt : String, val totalLikeCnt : Int, val isLike : String)