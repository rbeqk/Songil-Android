package com.example.songil.page_comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommentViewModel : ViewModel(){
    var comment = ""
    var commentBtnActivate = MutableLiveData<Boolean>()

    fun checkCommentLength(){
        commentBtnActivate.value = (comment.length in 1..300)
    }
}