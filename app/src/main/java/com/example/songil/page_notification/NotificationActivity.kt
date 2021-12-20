package com.example.songil.page_notification

import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.NoticeActivityBinding

class NotificationActivity : BaseActivity<NoticeActivityBinding>(R.layout.notice_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.notice.text = getString(R.string.notice)

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