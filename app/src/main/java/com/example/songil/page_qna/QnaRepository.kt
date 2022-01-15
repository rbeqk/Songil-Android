package com.example.songil.page_qna

import com.example.songil.config.GlobalApplication
import com.example.songil.data.Chat
import com.example.songil.data.WithQna
import com.example.songil.repository.PostRepository

class QnaRepository : PostRepository() {

    private val retrofitInterface = GlobalApplication.sRetrofit.create(QnaRetrofitInterface::class.java)

    override suspend fun getChat(postIdx: Int, pageIdx: Int): ArrayList<Chat> {
        retrofitInterface.getComments(postIdx, pageIdx).let { response ->
            if (response.isSuccessful){
                if (response.body()?.code == 200){
                    return response.body()!!.result
                }
            }
        }
        return arrayListOf()
    }

    suspend fun getQna(qnaIdx : Int) : WithQna? {
        retrofitInterface.getQna(qnaIdx).let { response ->
            if (response.isSuccessful){
                if (response.body()?.code == 200){
                    return response.body()!!.result
                }
            }
        }
        return null
    }
}