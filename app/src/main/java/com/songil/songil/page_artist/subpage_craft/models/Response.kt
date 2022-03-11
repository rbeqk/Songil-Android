package com.songil.songil.page_artist.subpage_craft.models

import com.songil.songil.config.BaseResponse
import com.songil.songil.data.PageCnt

data class ResponseArtistItemCnt(val result : PageCnt) : BaseResponse()

data class ResponseCraftList(val result : CraftAndCraftCnt) : BaseResponse()