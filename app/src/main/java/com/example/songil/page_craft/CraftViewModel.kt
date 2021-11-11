package com.example.songil.page_craft

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.page_craft.models.CraftBaseInfo
import com.example.songil.page_craft.models.CraftDetailInfo
import com.example.songil.page_craft.models.CraftReview
import com.example.songil.page_craft.models.RequestCarts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CraftViewModel : ViewModel() {
    private val repository = CraftRepository()

    var resultCode = MutableLiveData<Int>()
    lateinit var baseInfo: CraftBaseInfo
    lateinit var detailInfo : CraftDetailInfo
    lateinit var reviews : ArrayList<CraftReview>
    var message = ""
    private var craftIdx = 0

    // detail, review, 1:1 ask fragment 로 전환하는 버튼의 활성화 여부, 그리고 현재 보여주는 fragment 의 idx
    var btnDetailActivate = MutableLiveData<Boolean>()
    var btnReviewActivate = MutableLiveData<Boolean>()
    var btnAskActivate = MutableLiveData<Boolean>()
    var currentIdx = MutableLiveData<Int>()

    // 구매할 product 개수
    var itemCount = MutableLiveData<Int>()

    // 장바구니 추가 호출 확인용
    var addCartResult = MutableLiveData<Int>()

    init {
        btnDetailActivate.value = true
        btnReviewActivate.value = false
        btnAskActivate.value = false
    }

    fun tryGetCraftInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getDetailCraftInfo(craftIdx).let { response ->
                if (response.isSuccessful){
                    if (response.body()!!.code == 1000){
                        baseInfo = response.body()!!.result.infoResult
                        detailInfo = response.body()!!.result.detailResult
                        reviews = response.body()!!.result.reviewResults
                        itemCount.postValue(1)
                    }
                    message = response.body()!!.message!!
                    resultCode.postValue(response.body()!!.code)
                }
            }
        }
    }


    fun tryAddToCart(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToCart(craftIdx, itemCount.value!!).let { response ->
                if (response.isSuccessful){
                    addCartResult.postValue(response.body()!!.code)
                }
            }
        }
    }


    fun setCraftIdx(idx : Int){
        craftIdx = idx
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