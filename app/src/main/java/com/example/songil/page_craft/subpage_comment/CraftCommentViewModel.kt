package com.example.songil.page_craft.subpage_comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.CraftComment
import kotlin.math.max

class CraftCommentViewModel : ViewModel() {
    var updateResult = MutableLiveData<Int>()
    var craftCommentList = ArrayList<CraftComment>()
    private var nextPage = 0

    fun tryGetReview(){
        if (nextPage > 0){
            val commentList = ArrayList<CraftComment>()
            commentList.add(CraftComment(1, 1, "프로브", "2021-12-18 00:47", null, "리뷰 내용입니다.", "N"))
            commentList.add(CraftComment(1, 1, "프로브", "2021-12-18 01:02", null, "리뷰 내용입니다.", "N"))
            commentList.add(CraftComment(1, 1, "프로브", "2021-12-18 01:22"
                , arrayListOf("https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/SongileImage%2Fabtest1.png?alt=media&token=f7631dd9-9fb1-4562-a9d7-aba1185ddd19")
                , "리뷰 내용입니다.", "N"))
            commentList.add(CraftComment(1, 1, "프로브", "2021-12-18 01:35"
                , arrayListOf("https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/SongileImage%2Fabtest1.png?alt=media&token=f7631dd9-9fb1-4562-a9d7-aba1185ddd19"
                , "https://firebasestorage.googleapis.com/v0/b/dietmemory-65737.appspot.com/o/SongileImage%2Fabtest2.png?alt=media&token=8e10a64b-3392-45d0-9de7-12fefa90d7cc")
                , "리뷰 내용입니다.", "N"))

            craftCommentList.addAll(commentList)
            updateResult.value = 200
            nextPage = max(0, nextPage - 1)
        }
    }

    fun setNextPage(){
        nextPage = 5
    }
}