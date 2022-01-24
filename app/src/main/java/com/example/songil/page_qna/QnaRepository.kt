package com.example.songil.page_qna

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.data.Chat
import com.example.songil.data.WithQna
import com.example.songil.page_qna.models.RequestWriteComment
import com.example.songil.page_qna.models.ResponseQnaLike
import com.example.songil.repository.PostRepository
import retrofit2.Response

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

    suspend fun writeChat(postIdx : Int, parentIdx : Int?, comment : String) : Int{
        val result = retrofitInterface.postQnaChat(postIdx, RequestWriteComment(parentIdx, comment))
        if (result.isSuccessful){
            return result.body()!!.code
        }
        return -1
    }

    suspend fun deleteChat(commentIdx : Int) : Int{
        val result = retrofitInterface.deleteQnaChat(commentIdx)
        if (result.isSuccessful){
            return result.body()!!.code
        }
        return -1
    }

    suspend fun changeQnaLike(qnaIdx : Int) : Response<ResponseQnaLike> {
        val result = retrofitInterface.patchQnaLike(qnaIdx)
        if (result.isSuccessful){
            return result
        } else {
            throw UnknownError()
        }
    }

    suspend fun deleteQna(qnaIdx : Int) : Response<BaseResponse> {
        val result = retrofitInterface.deleteQna(qnaIdx)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}