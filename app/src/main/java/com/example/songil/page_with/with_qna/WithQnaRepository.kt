package com.example.songil.page_with.with_qna
import com.example.songil.data.WithQna

class WithQnaRepository {
    fun tempGetQna(pageIdx : Int) : ArrayList<WithQna>{
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
    }
}