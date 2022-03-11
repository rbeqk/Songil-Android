package com.songil.songil.recycler.rv_interface

interface RvPostAndChatView {
    // chat
    fun clickReply(parentIdx : Int, userName : String ?= null)

    fun removeChat(commentIdx : Int)

    fun reportChat(commentIdx : Int)

    // header qna
    fun clickLikeBtn()

    // header ab-test
    fun vote(abTestIdx : Int, vote : String)

    fun cancelVote(abTestIdx : Int)
}