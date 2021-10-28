package com.example.songil.test_area

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.TestActivityBinding

class TestActivity : BaseActivity<TestActivityBinding>(R.layout.test_activity) {

    var btn1Activate = MutableLiveData<Boolean>()
    var btn2Activate = MutableLiveData<Boolean>()
    var btn3Activate = MutableLiveData<Boolean>()


    init {
        btn1Activate.value = true
        btn2Activate.value = false
        btn3Activate.value = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.data = this
        binding.lifecycleOwner = this
    }

    fun changeSelect(idx : Int){
        when (idx){
            0 -> {
                btn1Activate.value = true
                btn2Activate.value = false
                btn3Activate.value = false
            }
            1 -> {
                btn1Activate.value = false
                btn2Activate.value = true
                btn3Activate.value = false
            }
            else -> {
                btn1Activate.value = false
                btn2Activate.value = false
                btn3Activate.value = true
            }
        }
    }

}