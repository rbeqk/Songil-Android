package com.example.songil.repository

import com.example.songil.data.Chat

abstract class PostRepository {
    abstract suspend fun getChat(postIdx : Int, pageIdx : Int) : ArrayList<Chat>
}