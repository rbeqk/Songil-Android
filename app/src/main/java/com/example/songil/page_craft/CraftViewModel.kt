package com.example.songil.page_craft

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.data.CraftAndAmount
import com.example.songil.data.CraftDetailInfo
import com.example.songil.data.LikeData
import kotlinx.coroutines.launch

class CraftViewModel : BaseViewModel() {
    private val repository = CraftRepository()

    var resultCode = MutableLiveData<Int>()
    var message = ""
    private var craftIdx = 0

    // 수정 후 api 에서 사용하는 데이터
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
            repository.getDetailCraftInfo(craftIdx).let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 200){
                        itemCount.postValue(1)
                        inStock.postValue(response.body()?.result?.isSoldOut == "N")
                        productDetailInfo = response.body()!!.result
                        likeData.postValue(LikeData(response.body()?.result?.isLike ?: "N", 0))
                    }
                    message = response.body()!!.message!!
                    resultCode.postValue(response.body()?.code ?: -1)
                }
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

}