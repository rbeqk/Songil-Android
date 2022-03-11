package com.songil.songil.page_craft.subpage_comment.models

import com.songil.songil.data.CraftComment

data class CraftCommentInfoWithCnt(val totalCommentCnt : Int, val comments : ArrayList<CraftComment>)