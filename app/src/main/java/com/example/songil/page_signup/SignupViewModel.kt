package com.example.songil.page_signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.config.BaseResponse
import com.example.songil.config.GlobalApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    var phoneNumber = ""
    var authNumber = ""
    var inputAuthNumber = ""
    var fragmentIdx = MutableLiveData<Int>()
    var authTimer = MutableLiveData<Int>()
    var isNext = false

    var terms1 = MutableLiveData<Boolean>(false)
    var terms2 = MutableLiveData<Boolean>(false)
    var terms3 = MutableLiveData<Boolean>(false)
    var terms4 = MutableLiveData<Boolean>(false)
    var termsAll = MutableLiveData<Boolean>(false)

    // 각 페이지에 가장 밑에 있는 버튼의 활성화 여부
    var btn1Activate = MutableLiveData<Boolean>()
    var btn2Activate = MutableLiveData<Boolean>()
    var btn3Activate = MutableLiveData<Boolean>()
    var btn4Activate = MutableLiveData<Boolean>()


    init {
        btn1Activate.value = false
        btn2Activate.value = false
        btn3Activate.value = false
        btn4Activate.value = false
        fragmentIdx.value = 0
    }

    fun setFragmentIdx(number : Int){
        isNext = (number > 0) // 다음으로 가는지, 이전으로 가는지에 따라 애니메이션 효과를 다르게 적용하기 위함
        fragmentIdx.value = fragmentIdx.value?.plus(number)
    }


    // fragment1 (약관 페이지)
    fun changeAll(){
        termsAll.value = !termsAll.value!!

        if (termsAll.value!!){
            terms1.value = true
            terms2.value = true
            terms3.value = true
            terms4.value = true
        } else {
            terms1.value = false
            terms2.value = false
            terms3.value = false
            terms4.value = false
        }
        checkBtn1Activate()
    }

    // fragment1 (약관 페이지)
    fun checkAll(idx : Int){
        when (idx){
            0 -> { terms1.value = !terms1.value!! }
            1 -> { terms2.value = !terms2.value!! }
            2 -> { terms3.value = !terms3.value!! }
            else -> { terms4.value = !terms4.value!! }
        }
        termsAll.value = (terms1.value!! && terms2.value!! && terms3.value!! && terms4.value!!)
        checkBtn1Activate()
    }

    // fragment1 (약관 페이지)
    private fun checkBtn1Activate() {
        btn1Activate.value = (terms1.value!! && terms2.value!! && terms3.value!! )
    }

    // fragment2 (번호 중복확인 페이지)
    fun checkPhoneNumberForm() {
        btn2Activate.value = phoneNumber.matches(Regex("^[0-9]{3}[0-9]{4}[0-9]{4}\$"))
    }

    // fragment3 (인증번호 입력 페이지)
    fun checkAuthNumberForm(){
        btn3Activate.value = inputAuthNumber.matches(Regex("[0-9]{4}\$"))
    }

    // network!!
    fun tryCheckPhoneNumberDuplicate(){
        val retrofitInterface = GlobalApplication.sRetrofit.create(SignupRetrofitInterface::class.java)
        retrofitInterface.getPhoneNumberDuplicateCheck(phoneNumber).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful){
                    if (response.body()!!.isSuccess){
                        when (response.body()!!.code){
                            1000 -> {
                                setFragmentIdx(1)
                            }
                            else -> {
                                Log.d("check phoneNumber", response.body()!!.message!!)
                            }
                        }
                    } else {
                        Log.d("check phoneNumber", response.body()!!.message!!)
                    }
                }
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("check phoneNumber", "onFailure...")
            }
        })
    }
}
