package com.example.songil.page_login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.songil.config.GlobalApplication
import com.example.songil.data.PhoneNumber
import com.example.songil.page_login.models.ResponseAuthPhone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var phoneNumber = ""//= MutableLiveData<String>()
    var authNumber = ""
    var btn1Activate = MutableLiveData<Boolean>()
    var btn2Activate = MutableLiveData<Boolean>()
    var inputAuthNumber = MutableLiveData<String>()


    init {
        btn1Activate.value = false
        btn2Activate.value = false
        inputAuthNumber.value = ""
    }

    fun checkPhoneNumberForm(){
        btn1Activate.value = phoneNumber.matches(Regex("^[0-9]{3}[0-9]{4}[0-9]{4}\$"))
    }

    fun checkAuthNumberForm(){
        btn2Activate.value = inputAuthNumber.value!!.matches(Regex("[0-9]{4}\$"))
    }

    fun checkAuthNumberValue() {
        if (inputAuthNumber.value == authNumber) {
            Log.d("login", "success")
        }
    }

    // network!!
    fun trySendPhoneNumber(successFunc : () -> Unit){
        val retrofitInterface = GlobalApplication.sRetrofit.create(LoginRetrofitInterface::class.java)
        retrofitInterface.postAuthPhone(PhoneNumber(phoneNumber)).enqueue(object : Callback<ResponseAuthPhone>{
            override fun onResponse(
                call: Call<ResponseAuthPhone>,
                response: Response<ResponseAuthPhone>
            ) {
                if (response.isSuccessful){
                    if (response.body()!!.isSuccess){
                        successFunc()
                    } else {
                        Log.d("sendPhoneNumber", response.body()!!.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseAuthPhone>, t: Throwable) {
                Log.d("sendPhoneNumber", t.toString())
            }

        })
    }
}