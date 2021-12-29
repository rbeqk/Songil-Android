package com.example.songil.page_craft

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.data.CraftDetailInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CraftViewModel : ViewModel() {
    private val repository = CraftRepository()

    var resultCode = MutableLiveData<Int>()
    var message = ""
    private var craftIdx = 0

    // 수정 후 api 에서 사용하는 데이터
    lateinit var productDetailInfo : CraftDetailInfo

    // 구매 버튼 활성화 여부
    var inStock = MutableLiveData<Boolean>(false)

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
                    if (response.body()!!.code == 200){
                        itemCount.postValue(1)
                        inStock.postValue(response.body()?.result?.isSoldOut == "N")
                        productDetailInfo = response.body()!!.result
                    }
                    message = response.body()!!.message!!
                    resultCode.postValue(response.body()?.code ?: -1)
                }
            }
        }
    }

    fun tempGetCraftInfo(){
        productDetailInfo = CraftDetailInfo(1, "Y", "Y" ,"https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA3MjFfMjQz%2FMDAxNjI2ODMwMzY4NDc4.idg1-Q4qxqxCSg-e4259B4Syr8Z05OmJ0YhupJdtPtwg.pZW6_b8MetkF9vp2s9sy9nada6Gj1JlgSD3akClUQyMg.JPEG.myohan6%2FDSC_2498.JPG&type=a340",
                "물결화병", 38000, arrayListOf("50000원 이하 5000원", "50000~100000원 2500원", "100000원 이상 무료"),
                arrayListOf("유리"), arrayListOf("시각적 만족감"), "물결화병에 대한 설명이\n들어갈 예정입니다.\n\n문의는 1:1 ask를 사용해주세요", "15x16x15(cm)", arrayListOf("유의사항1", "유의사항2"),
                arrayListOf("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA5MTBfMjEz%2FMDAxNjMxMjYyMTM1MDM2.-SRY3A7o4aSsLjMVqm8h2nEZQcLDowaxwG2vDgBw76Ig.fqAqJK87AzAfvX-NLNk3bpeUt-Ibk8VufXOpYZo05PIg.JPEG.redjudy%2F%25C4%25AB%25B5%25E5%25C1%25F6%25B0%25A9_%25BA%25B0%25B9%25E3_%25283%2529.JPG&type=a340",
                        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA3MjFfMjQz%2FMDAxNjI2ODMwMzY4NDc4.idg1-Q4qxqxCSg-e4259B4Syr8Z05OmJ0YhupJdtPtwg.pZW6_b8MetkF9vp2s9sy9nada6Gj1JlgSD3akClUQyMg.JPEG.myohan6%2FDSC_2498.JPG&type=a340"), 1,
                "조민지 작가", "조민지 작가에 대한 소개입니다.", 12)
        resultCode.postValue(200)

        itemCount.postValue(1)
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