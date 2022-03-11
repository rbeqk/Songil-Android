package com.songil.songil.page_main

import android.content.Intent
import android.os.Bundle
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.MainActivityBinding
import com.songil.songil.page_article.ArticleFragmentMain
import com.songil.songil.page_artistmanage.ArtistManageFragment
import com.songil.songil.page_home.HomeFragment
import com.songil.songil.page_mypage.MypageFragment
import com.songil.songil.page_orderstatus.OrderStatusActivity
import com.songil.songil.page_shop.ShopFragmentMain
import com.songil.songil.page_with.WithFragment
import com.songil.songil.utils.setStatusBarBlack

class MainActivity : BaseActivity<MainActivityBinding>(R.layout.main_activity){

    private var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_home -> {
                    setStatusBarBlack(this, false)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, HomeFragment()).commit()
                }
                R.id.bottom_shop -> {
                    setStatusBarBlack(this, false)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, ShopFragmentMain()).commit()
                }
                R.id.bottom_with -> {
                    setStatusBarBlack(this, true)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, WithFragment()).commit()
                }
                R.id.bottom_article -> {
                    setStatusBarBlack(this, true)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, ArticleFragmentMain()).commit()
                }
                else -> {
                    setStatusBarBlack(this, false)
                    supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(binding.layoutFragment.id, MypageFragment()).commit()
                }
            }
            true
        }
        binding.bottom.selectedItemId = R.id.bottom_home
    }

    fun toggleMyPage(toArtist : Boolean){
        if (toArtist) { supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, ArtistManageFragment()).commit() }
        else { supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, MypageFragment()).commit() }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val direct = intent?.getIntExtra("TARGET_DIRECTION", 0)
        direct?.let { targetIdx ->
            when (targetIdx){
                1 -> { // 결제 완료 이후 주문 현황 페이지로 이동
                    binding.bottom.selectedItemId = R.id.bottom_my
                    startActivityHorizontal(Intent(this, OrderStatusActivity::class.java))
                }
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - waitTime >= 1500){
            waitTime = System.currentTimeMillis()
            showSimpleToastMessage("뒤로가기 버튼을 한번 더 누르면 종료됩니다.")
        } else {
            finish()
        }
    }
}