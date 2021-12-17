package com.example.songil.page_with.with_abtest

import com.example.songil.data.ABTest
import com.example.songil.data.ABTestViewInfo
import com.example.songil.data.ABVoteInfo

class WithAbtestRepository {
    fun tempGetAbTest(pageIdx : Int) : ArrayList<ABTestViewInfo>{
        val temp = arrayListOf<ABTest>()

        if (pageIdx == 0){
            return arrayListOf()
        }

        temp.add(ABTest(pageIdx * 3 + 0, 1, "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg", "손길${pageIdx * 3 + 0}"
                , "두 가지 최종제품중에 무슨 색상으로 출시할지 고민됩니다. 둘 다 너무 이뻐서 투표받으려고 해요. 어떤 색상이 더 좋으신가요?"
                , "https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/SongileImage%2Fabtest1.png?alt=media&token=f7631dd9-9fb1-4562-a9d7-aba1185ddd19"
                ,"https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/SongileImage%2Fabtest2.png?alt=media&token=8e10a64b-3392-45d0-9de7-12fefa90d7cc"
                , "~ 2021.12.12 까지", 12, "Y", null, ABVoteInfo("B", 150, 60)
        ))
        temp.add(ABTest(pageIdx * 3 + 1, 1, "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg", "손길${pageIdx * 3 + 1}"
                , "두 가지 최종제품중에 무슨 색상으로 출시할지 고민됩니다. 둘 다 너무 이뻐서 투표받으려고 해요. 어떤 색상이 더 좋으신가요?"
                , "https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/SongileImage%2Fabtest1.png?alt=media&token=f7631dd9-9fb1-4562-a9d7-aba1185ddd19"
                ,"https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/SongileImage%2Fabtest2.png?alt=media&token=8e10a64b-3392-45d0-9de7-12fefa90d7cc"
                , "~ 2021.12.12 까지", 12, "N", null, null
        ))
        temp.add(ABTest(pageIdx * 3 + 2, 1, "https://cdn.pixabay.com/photo/2020/10/14/03/18/man-5653200_960_720.jpg", "손길${pageIdx * 3 + 2}"
                , "두 가지 최종제품중에 무슨 색상으로 출시할지 고민됩니다. 둘 다 너무 이뻐서 투표받으려고 해요. 어떤 색상이 더 좋으신가요?"
                , "https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/SongileImage%2Fabtest1.png?alt=media&token=f7631dd9-9fb1-4562-a9d7-aba1185ddd19"
                ,"https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/SongileImage%2Fabtest2.png?alt=media&token=8e10a64b-3392-45d0-9de7-12fefa90d7cc"
                , "~ 2021.12.12 까지", 12, "N", ABVoteInfo("B", 150, 60), null
        ))

        val temp2 = arrayListOf<ABTestViewInfo>()
        for (t in temp){
            val choice = t.voteInfo?.voteImage
            temp2.add(ABTestViewInfo(t, choice))
        }
        return temp2
    }
}