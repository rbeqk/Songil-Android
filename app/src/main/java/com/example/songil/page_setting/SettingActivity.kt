package com.example.songil.page_setting

import android.content.Intent
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.SettingActivityBinding
import com.example.songil.page_notice.NoticeActivity
import com.example.songil.page_withdrawal.WithdrawalActivity
import com.example.songil.recycler.adapter.PdfTermAdapter
import com.example.songil.utils.copyInputStreamToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class SettingActivity : BaseActivity<SettingActivityBinding>(R.layout.setting_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
        setButton()
    }

    private fun setRecyclerView(){
        binding.rvTerm.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvTerm.adapter = PdfTermAdapter(null, getWindowSize()[0])
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tvbtnNotice.setOnClickListener {
            startActivityHorizontal(Intent(this, NoticeActivity::class.java))
        }

        binding.tvbtnPrivacyPolicy.setOnClickListener {
            showTermLayout()
            binding.tvTerms.text = getString(R.string.terms_privacy_policy)
            lifecycleScope.launch(Dispatchers.Default) {
                val result = kotlin.runCatching {
                    val inputStream = resources.assets.open("songil_privacy_policy.pdf")
                    val file = File.createTempFile(inputStream.hashCode().toString(), ".pdf")
                    copyInputStreamToFile(inputStream, file)

                    val input = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                    val renderer = PdfRenderer(input)
                    withContext(Dispatchers.Main){
                        (binding.rvTerm.adapter as PdfTermAdapter).changeRenderer(renderer)
                    }
                    file.delete()
                }
                /*if (result.isFailure){
                    //에러처리 어떻게
                }*/
            }
        }

        binding.tvbtnUseTerm.setOnClickListener {
            showTermLayout()
            binding.tvTerms.text = getString(R.string.use_term)
            lifecycleScope.launch(Dispatchers.Default) {
                val result = kotlin.runCatching {
                    val inputStream = resources.assets.open("songil_terms_of_use.pdf")
                    val file = File.createTempFile(inputStream.hashCode().toString(), ".pdf")
                    copyInputStreamToFile(inputStream, file)

                    val input = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                    val renderer = PdfRenderer(input)
                    withContext(Dispatchers.Main){
                        (binding.rvTerm.adapter as PdfTermAdapter).changeRenderer(renderer)
                    }
                    file.delete()
                }
                /*if (result.isFailure){
                    //에러처리 어떻게
                }*/
            }
        }

        binding.btnConfirmation.setOnClickListener {
            hideTermLayout()
        }

        binding.backgroundTerm.setOnClickListener {
            hideTermLayout()
        }

        binding.tvWithdrawal.setOnClickListener {
            startActivityHorizontal(Intent(this, WithdrawalActivity::class.java))
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    private fun showTermLayout() {
        val backgroundAnim = AlphaAnimation(0f, 1f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = true
        val anim = TranslateAnimation(0f, 0f, binding.layoutTerm.height.toFloat(), 0f)
        anim.duration = 350
        anim.fillAfter = true

        binding.backgroundTerm.animation = backgroundAnim
        binding.backgroundTerm.visibility = View.VISIBLE
        binding.backgroundToolbar.animation = backgroundAnim
        binding.backgroundToolbar.visibility = View.VISIBLE
        binding.layoutTerm.animation = anim
        binding.layoutTerm.visibility = View.VISIBLE
    }

    private fun hideTermLayout(){
        val backgroundAnim = AlphaAnimation(1f, 0f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = false
        val anim = TranslateAnimation(0f, 0f, 0f, binding.layoutTerm.height.toFloat())
        anim.duration = 350
        anim.fillAfter = false

        binding.backgroundTerm.animation = backgroundAnim
        binding.backgroundTerm.visibility = View.INVISIBLE
        binding.backgroundToolbar.animation = backgroundAnim
        binding.backgroundToolbar.visibility = View.INVISIBLE
        binding.layoutTerm.animation = anim
        binding.layoutTerm.visibility = View.INVISIBLE
        (binding.rvTerm).scrollToPosition(0)
    }

    private fun getWindowSize() : ArrayList<Int> {
        val height : Int
        val width : Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            val displayMetrics = resources.displayMetrics
            height = displayMetrics.heightPixels//windowMetrics.bounds.height()
            width = displayMetrics.widthPixels//windowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(displayMetrics)
            height = displayMetrics.heightPixels
            width = displayMetrics.widthPixels
        }
        return arrayListOf(width, height)
    }
}