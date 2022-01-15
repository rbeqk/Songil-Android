package com.example.songil.page_story.subpage_story_chat

import com.example.songil.config.GlobalApplication
import com.example.songil.data.Chat
import com.example.songil.repository.PostRepository

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

}