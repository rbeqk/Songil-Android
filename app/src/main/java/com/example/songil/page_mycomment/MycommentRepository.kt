package com.example.songil.page_mycomment

import com.example.songil.config.GlobalApplication
import com.example.songil.data.Comment
import com.example.songil.data.CraftSimple
import com.example.songil.data.UserSimple

class MycommentRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(MycommentRetrofitInterface::class.java)

    suspend fun getMyWritableComment(page : Int) : List<CraftSimple> {
        val result = retrofit.getMyWritableComment(page = page)
        if (result.isSuccessful && result.body()?.code == 200){
            return result.body()!!.result.map {
                CraftSimple(craftIdx = it.craftIdx, mainImageUrl = it.mainImageUrl, craftName = it.name, artist = UserSimple(it.artistIdx, it.artistName), commentWritable = true)
            }
        }
        else throw UnknownError()
    }

    suspend fun getMyWrittenComment(page : Int) : List<Comment> {
        val result = retrofit.getMyWrittenComment(page = page)
        if (result.isSuccessful && result.body()?.code == 200) {
            return result.body()!!.result.map {
                Comment(commentIdx = it.commentIdx, craftIdx = it.craftIdx, craftName = it.name, artist = UserSimple(it.artistIdx, it.artistName), createdAt = it.createdAt, commentImageList = it.imageUrl, content = it.content)
            }
        }
        else throw UnknownError()
    }

    suspend fun getMyWritableCommentCnt() : Int {
        val result = retrofit.getMyWritableComment(page = 1)
        if (result.isSuccessful && result.body()?.code == 200){
            return result.body()!!.result.size
        }
        else throw UnknownError()
    }

    suspend fun getMyWrittenCommentCnt() : Int {
        val result = retrofit.getMyWrittenComment(page = 1)
        if (result.isSuccessful && result.body()?.code == 200){
            return result.body()!!.result.size
        }
        else throw UnknownError()
    }
}