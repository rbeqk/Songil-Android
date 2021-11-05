package com.example.songil.page_craft

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.CraftActivityBinding
import com.example.songil.page_craft.subpage_ask.CraftFragmentAsk
import com.example.songil.page_craft.subpage_detail.CraftFragmentDetail

class CraftActivity : BaseActivity<CraftActivityBinding>(R.layout.craft_activity) {

    private lateinit var viewModel : CraftViewModel
    private lateinit var detailFragment : CraftFragmentDetail
    private lateinit var askFragment : CraftFragmentAsk
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idx = intent.getIntExtra(GlobalApplication.CRAFT_IDX, 0)

        viewModel = ViewModelProvider(this)[CraftViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val resultCodeObserver = Observer<Int>{ liveData ->
            when (liveData) {
                1000 -> {
                    applyToView()
                    addFragments()
                }
                else -> {
                    Toast.makeText(this, viewModel.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.resultCode.observe(this, resultCodeObserver)

        val fragmentIdxObserver = Observer<Int>{ liveData ->
            when (liveData){
                0 -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).commit()
                    supportFragmentManager.beginTransaction().show(detailFragment).commit()
                    currentFragment = detailFragment
                }
                1 -> {
                    Log.d("review page", "not ready")
                }
                else -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).commit()
                    supportFragmentManager.beginTransaction().show(askFragment).commit()
                    currentFragment = askFragment
                }
            }
        }
        viewModel.currentIdx.observe(this, fragmentIdxObserver)


        viewModel.setCraftIdx(idx)

        viewModel.tryGetCraftInfo()

        /*supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, CraftFragmentDetail()).commit()*/
    }

    private fun applyToView(){
        Glide.with(this).load(viewModel.baseInfo.thumbNailImg).into(binding.ivThumbnail)
        Glide.with(this).load(viewModel.baseInfo.artistProfileImg).into(binding.ivProfile)
        binding.tvCraftName.text = viewModel.baseInfo.productName
        binding.tvPrice.text = getString(R.string.form_price_won, viewModel.baseInfo.price)
        binding.tvShippingFee.text = viewModel.baseInfo.shippingFee
        binding.tvMaterial.text = viewModel.baseInfo.productSubject
        binding.tvUsage.text = viewModel.baseInfo.purpose
        binding.tvMaker.text = viewModel.baseInfo.artistName
        binding.tvIntroduce.text = viewModel.baseInfo.artistIntro
        binding.tvReviewCount.text = getString(R.string.form_number_bracket, viewModel.baseInfo.reviewCount)
        binding.tvAskCount.text = getString(R.string.form_number_bracket, viewModel.baseInfo.askCount)
        if (viewModel.baseInfo.NewOrNot == "NOT NEW") binding.tvNew.visibility = View.GONE
    }

    private fun addFragments(){
        askFragment = CraftFragmentAsk(viewModel.baseInfo.artistProfileImg, viewModel.baseInfo.artistName)
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, askFragment).commit()
        supportFragmentManager.beginTransaction().hide(askFragment).commit()
        detailFragment = CraftFragmentDetail(viewModel.baseInfo.likeOrNot, viewModel.detailInfo)
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, detailFragment).commit()
        currentFragment = detailFragment
    }
}