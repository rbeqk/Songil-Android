package com.example.songil.config

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.example.songil.R

abstract class BaseActivity<B : ViewBinding> (@LayoutRes val layoutRes: Int) : AppCompatActivity() {
    protected lateinit var binding : B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)

    }

    fun onBackPressedVertical(){
        finish()
        overridePendingTransition(R.anim.none, R.anim.to_bottom)
    }

    fun onBackPressedHorizontal(){
        finish()
        overridePendingTransition(R.anim.from_left_30, R.anim.to_right)
    }

    fun startActivityVertical(intent : Intent){
        startActivity(intent)
        overridePendingTransition(R.anim.from_bottom, R.anim.none)
    }

    fun startActivityHorizontal(intent : Intent){
        startActivity(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }
}