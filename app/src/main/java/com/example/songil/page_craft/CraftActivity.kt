package com.example.songil.page_craft

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.CraftActivityBinding
import com.example.songil.page_craft.shbpage_review.CraftFragmentReview
import com.example.songil.page_craft.subpage_ask.CraftFragmentAsk
import com.example.songil.page_craft.subpage_detail.CraftFragmentDetail
import com.example.songil.page_inquiry.InquiryActivity

class CraftActivity : BaseActivity<CraftActivityBinding>(R.layout.craft_activity) {

    private lateinit var viewModel : CraftViewModel
    private lateinit var detailFragment : CraftFragmentDetail
    private lateinit var reviewFragment : CraftFragmentReview
    private lateinit var askFragment : CraftFragmentAsk
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idx = intent.getIntExtra(GlobalApplication.CRAFT_IDX, 0)

        viewModel = ViewModelProvider(this)[CraftViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setObserver()
        setButton()

        viewModel.setCraftIdx(idx)

        viewModel.tryGetCraftInfo()

        /*supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, CraftFragmentDetail()).commit()*/
    }

    private fun setObserver(){
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
                    showBuyBtn()
                }
                1 -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).commit()
                    supportFragmentManager.beginTransaction().show(reviewFragment).commit()
                    currentFragment = reviewFragment
                    showBuyBtn()
                }
                else -> {
                    supportFragmentManager.beginTransaction().hide(currentFragment).commit()
                    supportFragmentManager.beginTransaction().show(askFragment).commit()
                    currentFragment = askFragment
                    showInquiryBtn()
                }
            }
        }
        viewModel.currentIdx.observe(this, fragmentIdxObserver)

        val productCountObserver = Observer<Int>{ liveData ->
            binding.tvCount.text = liveData.toString()
            binding.tvAddPrice.text = getString(R.string.form_price_won, (liveData * viewModel.baseInfo.price))
        }
        viewModel.itemCount.observe(this, productCountObserver)

        val addToCartObserver = Observer<Int>{ liveData ->
            if (liveData == 1000){
                /*hideAddView()*/
                clearBottomButtonState()
            }
        }
        viewModel.addCartResult.observe(this, addToCartObserver)
    }

    private fun setButton(){
        binding.btnBuy.setOnClickListener {
            binding.btnFavorite.visibility = View.INVISIBLE
            binding.btnShare.visibility = View.INVISIBLE
            binding.btnBuy.visibility = View.INVISIBLE
            binding.btnAddToCart.visibility = View.VISIBLE
            binding.btnBuyNow.visibility = View.VISIBLE
            binding.btnAddToCart.visibility = View.VISIBLE
            showAddView()
        }

        binding.btnFavorite.setOnClickListener {
            Log.d("btnFav", "click")
        }

        binding.btnShare.setOnClickListener {
            Log.d("btnShare", "click")
        }

        binding.ivArrowDown.setOnClickListener {
            clearBottomButtonState()
        }

        binding.btnBuyNow.setOnClickListener {
            Log.d("btnBuyNow", "click")
        }

        binding.btnAddToCart.setOnClickListener {
            viewModel.tryAddToCart()
        }

        binding.layoutContract.setOnClickListener {
            startActivity(Intent(this, InquiryActivity::class.java))
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
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
        if (viewModel.baseInfo.NewOrNot == "NOT NEW") binding.tvNew.visibility = View.GONE
        binding.tvMakerCraft.text = getString(R.string.form_artist_craft, viewModel.baseInfo.artistName, viewModel.baseInfo.productName)
    }

    private fun addFragments(){
        askFragment = CraftFragmentAsk(viewModel.baseInfo.artistProfileImg, viewModel.baseInfo.artistName)
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, askFragment).commit()
        supportFragmentManager.beginTransaction().hide(askFragment).commit()
        reviewFragment = CraftFragmentReview(viewModel.reviews)
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, reviewFragment).commit()
        supportFragmentManager.beginTransaction().hide(reviewFragment).commit()
        detailFragment = CraftFragmentDetail(viewModel.baseInfo.likeOrNot, viewModel.detailInfo)
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, detailFragment).commit()
        currentFragment = detailFragment
        showBuyBtn()
    }

    private fun showBuyBtn(){
        binding.layoutBuy.visibility = View.VISIBLE
        binding.layoutContract.visibility = View.GONE
    }

    private fun showInquiryBtn(){
        binding.layoutBuy.visibility = View.GONE
        binding.layoutContract.visibility = View.VISIBLE
        clearBottomButtonState()
    }

    private fun showAddView(){
        if (binding.viewCraftAdd.visibility != View.VISIBLE) {
            val anim = TranslateAnimation(0f, 0f, binding.viewCraftAdd.height.toFloat(), 0f)
            anim.duration = 500
            anim.fillAfter = false
            binding.viewCraftAdd.animation = anim
            binding.viewCraftAdd.visibility = View.VISIBLE
        }
    }

    private fun hideAddView(){
        if (binding.viewCraftAdd.visibility == View.VISIBLE) {
            val anim = TranslateAnimation(0f, 0f, 0f, binding.viewCraftAdd.height.toFloat())
            anim.duration = 500
            anim.fillAfter = false
            binding.viewCraftAdd.animation = anim
            binding.viewCraftAdd.visibility = View.INVISIBLE
        }
    }

    private fun clearBottomButtonState(){
        binding.btnBuyNow.visibility = View.INVISIBLE
        binding.btnAddToCart.visibility = View.INVISIBLE
        binding.btnFavorite.visibility = View.VISIBLE
        binding.btnShare.visibility = View.VISIBLE
        binding.btnBuy.visibility = View.VISIBLE
        hideAddView()
    }
}