package com.songil.songil.repository

import com.songil.songil.data.Chat

abstract class PostRepository {
    abstract suspend fun getChat(postIdx : Int, pageIdx : Int) : ArrayList<Chat>
}