package com.example.songil.page_setting

import android.content.Intent
import android.os.Bundle
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.SettingActivityBinding
import com.example.songil.page_notification.NotificationActivity

class SettingActivity : BaseActivity<SettingActivityBinding>(R.layout.setting_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButton()
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.tvbtnNotice.setOnClickListener {
            startActivityHorizontal(Intent(this, NotificationActivity::class.java))
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}