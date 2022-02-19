package com.example.songil.page_with.with_qna
import com.example.songil.config.GlobalApplication
import com.example.songil.data.WithQna

class WithQnaRepository {

    private val retrofit = GlobalApplication.sRetrofit.create(WithQnaRetrofitInterface::class.java)

    suspend fun getQna(pageIdx : Int, sort : String) : ArrayList<WithQna> {
        val result = retrofit.getQna("qna", sort = sort, page = pageIdx)
        if (result.isSuccessful && result.body()?.code == 200){
            return result.body()!!.result
        } else {
            throw UnknownError()
        }
    }

    suspend fun getQnaPageCnt() : Int {
        val result = retrofit.getQnaPageCnt()
        if (result.isSuccessful){
            if (result.body()?.code == 200) {
                return result.body()!!.result.totalPages
            }
        }
        return -1
    }
}