package com.example.songil.data

data class ArticleDetailInfo(val articleIdx : Int, val mainImageUrl : String, val title : String, val editorIdx : Int,
                             val editorName : String, val createdAt : String, val content : ArrayList<ArticleContentInfo>)

data class ArticleContentInfo(val index : Int, val type : Int, val textData : String? = null, val imageData : String?= null, val productData : ProductInArticle ?= null)

data class ProductInArticle(val productIdx : Int, val name : String, val imageUrl : String, val artistName : String, val price : Int)