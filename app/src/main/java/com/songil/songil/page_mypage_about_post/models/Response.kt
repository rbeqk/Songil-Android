package com.songil.songil.page_mypage_about_post.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.Post

data class ResponseMyPagePost(val result : ArrayList<Post>) : BaseResponse()