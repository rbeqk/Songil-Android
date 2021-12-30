package com.example.songil.page_craft.subpage_comment.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.PageCnt

data class ResponseCraftComment(val result : CraftCommentInfoWithCnt) : BaseResponse()

data class ResponseCraftCommentPageCnt(val result : PageCnt) : BaseResponse()