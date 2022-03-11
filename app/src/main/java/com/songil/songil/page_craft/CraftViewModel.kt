package com.songil.songil.page_craft

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.songil.songil.config.BaseViewModel
import com.songil.songil.data.CraftAndAmount
import com.songil.songil.data.CraftDetailInfo
import com.songil.songil.data.LikeData
import kotlinx.coroutines.launch

class CraftViewModel : BaseViewModel() {
    private val repository = CraftRepository()

    var resultCode = MutableLiveData<Int>()
    var message = ""
    private var craftIdx = 0

    // 상품 정보
    lateinit var productDetailInfo : CraftDetailInfo

    // 구매 버튼 활성화 여부
    var inStock = MutableLiveData(false)

    // detail, review, 1:1 ask fragment 로 전환하는 버튼의 활성화 여부, 그리고 현재 보여주는 fragment 의 idx
    var btnDetailActivate = MutableLiveData<Boolean>()
    var btnReviewActivate = MutableLiveData<Boolean>()
    var btnAskActivate = MutableLiveData<Boolean>()
    var currentIdx = MutableLiveData<Int>()

    // 구매할 product 개수
    var itemCount = MutableLiveData<Int>()

    // 장바구니 추가 호출 확인용
    var addCartResult = MutableLiveData<Int>()

    // 좋아요 데이터
    var likeData = MutableLiveData<LikeData>()

    init {
        btnDetailActivate.value = true
        btnReviewActivate.value = false
        btnAskActivate.value = false
    }

    fun tryGetCraftInfo(){
        viewModelScope.launch(exceptionHandler) {
            val result =  repository.getDetailCraftInfo(craftIdx)
            if (result.code == 200){
                itemCount.postValue(1)
                inStock.postValue(result.result.isSoldOut == "N")
                productDetailInfo = result.result
                likeData.postValue(LikeData(result.result.isLike, 0))
            }
            message = result.message ?: ""
            resultCode.postValue(result.code)
        }
    }

    // 좋아요만 요청하는 함수 (실제로는 craft 전체 데이터를 요청하고, 그 중 좋아요만 반영)
    fun tryGetLike(){
        viewModelScope.launch(exceptionHandler) {
            val result =  repository.getDetailCraftInfo(craftIdx)
            if (result.code == 200){
                likeData.postValue(LikeData(result.result.isLike, 0))
            }
        }
    }

    fun tryToggleLike(){
        viewModelScope.launch(exceptionHandler){
            val result = repository.toggleLike(craftIdx)
            if (result.body()?.code == 200){
                likeData.postValue(LikeData(result.body()!!.result.isLike, result.body()!!.result.totalLikeCnt))
            }
        }
    }

    fun tryAddToCart(){
        viewModelScope.launch(exceptionHandler) {
            val result = repository.addToCart(craftIdx, itemCount.value!!)
            addCartResult.postValue(result.body()?.code ?: -1)
        }
    }


    fun setCraftIdx(idx : Int){
        craftIdx = idx
    }

    fun getOrderCraftForm() : CraftAndAmount {
        return CraftAndAmount(craftIdx, itemCount.value!!)
    }

    fun setBtnActivate(idx : Int){
        when (idx) {
            0 -> {
                btnDetailActivate.value = true
                btnReviewActivate.value = false
                btnAskActivate.value = false
            }
            1 -> {
                btnDetailActivate.value = false
                btnReviewActivate.value = true
                btnAskActivate.value = false
            }
            else -> {
                btnDetailActivate.value = false
                btnReviewActivate.value = false
                btnAskActivate.value = true
            }
        }
        currentIdx.value = idx
    }

    fun setItemCount(number : Int) {
        val tempCount = itemCount.value!!.plus(number)
        if (tempCount in 1..10)
            itemCount.value = tempCount
    }

    fun shareMessage() : String {
        return "[songil]\n${productDetailInfo.artistName}님의 ${productDetailInfo.name}\n가격 : ${productDetailInfo.price}\n용도 : ${productDetailInfo.usage.joinToString(",")}"
    }

}