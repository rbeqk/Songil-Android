package com.songil.songil.data

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable

// home 에서만 사용되는듯?
data class CraftSimpleInfo(val craftIdx : Int = 0, val mainImageUrl : String, val name : String, val artistIdx : Int, val artistName : String, val price : Int ?= null, val isNew : String ?= null)

data class CraftDetailInfo(val craftIdx : Int, val isNew : String, val isSoldOut : String, val mainImageUrl : String, val name : String,
                           val price : Int, val shippingFee : ArrayList<String>, val material : ArrayList<String>,
                           val usage : ArrayList<String>, val content : String, val size : String, val cautions : ArrayList<String>,
                           val refundInfo : ArrayList<String>, val deliveryPeriodInfo : ArrayList<String>,
                           val detailImageUrls : ArrayList<String>, val artistIdx : Int, val artistName : String, val artistIntroduction : String, val totalCommentCnt : Int,
                           val artistImageUrl : String? = null, var isLike : String)

data class Craft2(val craftIdx : Int, val mainImageUrl : String, val name : String, val artistName : String, val price : Int, val isNew : String)

data class Craft4(val craftIdx : Int, val mainImageUrl : String, val name : String, val artistIdx : Int, val artistName : String, val price : Int, val amount : Int)

data class CraftComment(val commentIdx : Int, val userIdx : Int, val nickname : String, val createdAt : String, val imageUrl : ArrayList<String>?, val content : String, val isReported : String)

data class Craft1(val craftIdx : Int, val mainImageUrl : String, val name : String, val artistIdx : Int, val artistName : String,
                  val price : Int, val isNew : String, val isSoldOut : String, var totalLikeCnt : Int, var isLike : String?, var totalCommentCnt : Int)

data class Craft3(val craftIdx : Int, val imageUrl : String, val artistName : String, val name : String, val createdAt : String, var writable : String)

data class CraftAndAmount(val craftIdx : Int, val amount : Int) : Serializable

// 리팩토링 이후 모든 Response Craft Data 를 아래 CraftSimple 로 전환하여 사용
class CraftSimple(
        val craftIdx : Int, val mainImageUrl : String, val craftName : String,
        val artist : UserSimple = UserSimple(), val likeData : LikeData = LikeData("N", 0),
        val price : Int = 0, var isNew : Boolean = false, var isSoldOut: Boolean = false,
        val orderDetailIdx : Int = 0, var commentWritable : Boolean = false
){
    companion object {
        val craftSimpleDiffCallback = object : DiffUtil.ItemCallback<CraftSimple>() {
            override fun areItemsTheSame(oldCraft: CraftSimple, newCraft: CraftSimple): Boolean {
               return (newCraft.likeData.isLike == oldCraft.likeData.isLike &&
                       newCraft.likeData.totalLikeCnt == oldCraft.likeData.totalLikeCnt &&
                       newCraft.isNew == oldCraft.isNew && newCraft.isSoldOut == oldCraft.isSoldOut)
            }

            override fun areContentsTheSame(oldCraft: CraftSimple, newCraft: CraftSimple): Boolean {
                return (newCraft.craftIdx == oldCraft.craftIdx)
            }

        }
    }
}