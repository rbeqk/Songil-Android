package com.example.songil.page_mypage_about_post.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.Post

data class ResponseMyPagePost(val result : ArrayList<Post>) : BaseResponse()