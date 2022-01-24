package com.example.songil.page_qnawrite

import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import com.example.songil.data.WithQna
import com.example.songil.page_qna.QnaRetrofitInterface
import com.example.songil.page_qnawrite.models.BodyQnaWrite
import com.example.songil.page_qnawrite.models.ResponseWriteQna
import retrofit2.HttpException
import retrofit2.Response

class QnaWriteRepository {
    private val writeService = GlobalApplication.sRetrofit.create(QnaWriteRetrofitInterface::class.java)
    // use page_qna.QnaRetrofitInterface to get Qna information
    private val qnaService = GlobalApplication.sRetrofit.create(QnaRetrofitInterface::class.java)

    suspend fun writeQna(title : String, content : String) : Response<ResponseWriteQna>{
        val result = writeService.postQna(BodyQnaWrite(title, content))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun modifyQna(qnaIdx : Int, title : String, content : String) : Response<BaseResponse>{
        val result = writeService.patchQna(qnaIdx, BodyQnaWrite(title, content))
        if (result.isSuccessful) return result
        else throw UnknownError()
    }

    suspend fun getQna(qnaIdx : Int) : WithQna {
        val result = qnaService.getQna(qnaIdx)
        if (result.isSuccessful){
            if (result.body()?.code == 200){
                return result.body()!!.result
            }
            else {
                throw HttpException(result)
            }
        } else {
            throw UnknownError()
        }
    }
}