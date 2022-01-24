package com.example.songil.recycler.rv_interface

interface RvPostAndChatView {
    // chat
    fun clickReply(parentIdx : Int, userName : String ?= null)

    fun removeChat(commentIdx : Int)

    fun reportChat(commentIdx : Int)

    // header qna
    fun clickLikeBtn()
}