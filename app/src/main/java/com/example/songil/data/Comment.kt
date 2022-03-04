package com.example.songil.data

import androidx.recyclerview.widget.DiffUtil

// 코멘트 작성도 이 클래스로 통합할 예정
class Comment(val commentIdx : Int, val craftIdx : Int, val craftName : String, val artist : UserSimple, val createdAt : String,
              val commentImageList : ArrayList<String>?, val content : String, var isRemoved : Boolean = false, var isReported : Boolean = false) {
    companion object {
        val commentDiffCallback = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.commentIdx == newItem.commentIdx
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return (oldItem.content == newItem.content && newItem.commentImageList == oldItem.commentImageList && newItem.isRemoved == oldItem.isRemoved && newItem.isReported == oldItem.isReported)
            }
        }
    }
}