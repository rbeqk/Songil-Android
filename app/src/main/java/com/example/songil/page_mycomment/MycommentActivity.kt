package com.example.songil.page_mycomment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.MycommentActivityBinding

class MycommentActivity : BaseActivity<MycommentActivityBinding>(R.layout.mycomment_activity) {

    private lateinit var viewModel : MycommentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MycommentViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setButton()
        setObserver()

    }

    private fun setObserver(){
        val fragmentIdxObserver = Observer<Int>{ liveData ->
            when (liveData){
                0 -> {}
                else -> {}
            }
        }
        viewModel.currentFragmentIdx.observe(this, fragmentIdxObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}