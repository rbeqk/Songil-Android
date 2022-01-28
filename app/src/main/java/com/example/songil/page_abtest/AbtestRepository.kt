package com.example.songil.page_abtest

import com.example.songil.config.GlobalApplication
import com.example.songil.data.ABTest
import com.example.songil.data.Chat
import com.example.songil.page_abtest.model.RequestWriteComment
import com.example.songil.repository.PostRepository
import retrofit2.HttpException

class AbtestRepository : PostRepository() {

    private val retrofitInterface = GlobalApplication.sRetrofit.create(AbtestRetrofitInterface::class.java)

    override suspend fun getChat(postIdx: Int, pageIdx: Int): ArrayList<Chat> {
        val response = retrofitInterface.getComments(postIdx, pageIdx)
        if (response.isSuccessful && response.body()?.code == 200){
            return response.body()!!.result
        }
        throw(HttpException(response))
    }

    suspend fun getAbtest(abtestIdx : Int) : ABTest{
        val response = retrofitInterface.getAbtest(abtestIdx)
        if (response.isSuccessful && response.body()?.code == 200){
            return response.body()!!.result
        }
        throw(HttpException(response))
    }

    suspend fun writeChat(postIdx : Int, parentIdx : Int?, comment : String) : Int {
        val response = retrofitInterface.postAbtestChat(postIdx, RequestWriteComment(parentIdx, comment))
        if (response.isSuccessful){
            return response.body()!!.code
        }
        return -1
    }

    suspend fun deleteChat(commentIdx : Int) : Int {
        val response = retrofitInterface.deleteAbtestChat(commentIdx)
        if (response.isSuccessful){
            return response.body()!!.code
        }
        return -1
    }
}