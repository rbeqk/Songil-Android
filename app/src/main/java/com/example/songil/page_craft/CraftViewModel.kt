package com.example.songil.page_craft

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.page_craft.models.CraftBaseInfo
import com.example.songil.page_craft.models.CraftDetailInfo
import com.example.songil.page_craft.models.CraftReview
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

    var btnDetailActivate = MutableLiveData<Boolean>()
    var btnReviewActivate = MutableLiveData<Boolean>()
    var btnAskActivate = MutableLiveData<Boolean>()
    var currentIdx = MutableLiveData<Int>()

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
                    }
                    message = response.body()!!.message!!
                    resultCode.postValue(response.body()!!.code)
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

}