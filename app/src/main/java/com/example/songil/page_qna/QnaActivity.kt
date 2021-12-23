package com.example.songil.page_qna

import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.QnaActivityBinding

class QnaActivity : BaseActivity<QnaActivityBinding>(R.layout.qna_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        onBackPressedHorizontal()
    }
}