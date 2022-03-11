package com.songil.songil.page_story.subpage_story_chat

import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.Chat
import com.songil.songil.page_story.subpage_story_chat.models.RequestWriteComment
import com.songil.songil.repository.PostRepository

class StoryChatRepository : PostRepository() {

    private val retrofitInterface = GlobalApplication.sRetrofit.create(StoryChatRetrofitInterface::class.java)

    override suspend fun getChat(postIdx: Int, pageIdx: Int): ArrayList<Chat> {
        retrofitInterface.getStoryChat(postIdx, pageIdx).let { response ->
            if (response.isSuccessful){
                if (response.body()?.code == 200){
                    return response.body()!!.result
                }
            }
        }
        return arrayListOf()
    }

    suspend fun writeChat(postIdx : Int, parentIdx : Int?, comment : String) : Int {
        val result = retrofitInterface.postStoryChat(postIdx, RequestWriteComment(parentIdx, comment))
        if (result.isSuccessful){
            return result.body()!!.code
        }
        return -1
    }

    suspend fun deleteChat(commentIdx : Int) : Int {
        val response = retrofitInterface.deleteStoryChat(commentIdx)
        if (response.isSuccessful){
            return response.body()!!.code
        }
        return -1
    }

}