package com.songil.songil.page_craft.subpage_comment.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.PageCnt

data class ResponseCraftComment(val result : CraftCommentInfoWithCnt) : BaseResponse()

data class ResponseCraftCommentPageCnt(val result : PageCnt) : BaseResponse()