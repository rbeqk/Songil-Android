package com.example.songil.page_with.with_qna
import com.example.songil.config.GlobalApplication
import com.example.songil.data.WithQna

class WithQnaRepository {

    private val retrofit = GlobalApplication.sRetrofit.create(WithQnaRetrofitInterface::class.java)

    suspend fun getQna(pageIdx : Int, sort : String) : ArrayList<WithQna> {
        val result = retrofit.getQna("qna", sort = sort, page = pageIdx)
        if (result.isSuccessful){
            if (result.body()?.code == 200) return result.body()!!.result
        }
        return arrayListOf()
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

    /*fun tempGetQna(pageIdx : Int) : ArrayList<WithQna>{
        val temp = arrayListOf<WithQna>()

        for (i in 19 downTo 0){
            val tempQna = WithQna(1, 1, null, "닉네임", "${i}번째 QnA 입니다.", "아무거나 적어볼께요\n가나다라마바사아야어여오요우유", "2021.12.08 20:20", "Y", 123, "Y", 100)
            temp.add(tempQna)
        }

        return if (pageIdx == 20){
            val temp2 = arrayListOf<WithQna>()
            temp2.addAll(temp.subList(10, temp.size - 1))
            temp2
        } else {
            temp
        }
    }*/
}