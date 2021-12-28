package com.example.songil.page_artist.subpage_craft.models

import com.example.songil.config.BaseResponse
import com.example.songil.data.PageCnt

data class ResponseArtistItemCnt(val result : PageCnt) : BaseResponse()

data class ResponseCraftList(val result : CraftAndCraftCnt) : BaseResponse()