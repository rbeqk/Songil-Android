package com.example.songil.page_craft

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.config.InquiryTarget
import com.example.songil.data.LikeData
import com.example.songil.databinding.CraftActivityBinding
import com.example.songil.page_craft.subpage_comment.CraftFragmentComment
import com.example.songil.page_craft.subpage_ask.CraftFragmentAsk
import com.example.songil.page_craft.subpage_detail.CraftFragmentDetail
import com.example.songil.page_inquiry.InquiryActivity
import com.example.songil.page_order.OrderActivity
import com.example.songil.page_search.SearchActivity
import com.example.songil.page_search.models.SearchCategory

class CraftActivity : BaseActivity<CraftActivityBinding>(R.layout.craft_activity) {

    private lateinit var viewModel : CraftViewModel
    private lateinit var detailFragment : CraftFragmentDetail
    private lateinit var reviewFragment : CraftFragmentComment
    private lateinit var askFragment : CraftFragmentAsk
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idx = intent.getIntExtra(GlobalApplication.CRAFT_IDX, 1)

        viewModel = ViewModelProvider(this)[CraftViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setObserver()
        setButton()
        setNestedScrollView()

        viewModel.setCraftIdx(idx)
        viewModel.tryGetCraftInfo()

        binding.layoutNested.run {
            header = binding.layoutSticky
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding.btnShoppingbasket.applyChange()
    }

    private fun setObserver(){
        val resultCodeObserver = Observer<Int>{ liveData ->
            when (liveData) {
                200 -> {
                    applyToView()
                    addFragments()
                }
                else -> {
                    showSimpleToastMessage(viewModel.message)
                }
            }
        }
        viewModel.resultCode.observe(this, resultCodeObserver)

        val fragmentIdxObserver = Observer<Int>{ liveData ->
            when (liveData){
                0 -> {
                    changeFragment(detailFragment)
                    showBuyBtn()
                }
                1 -> {
                    changeFragment(reviewFragment)
                    showBuyBtn()
                }
                else -> {
                    changeFragment(askFragment)
                    showInquiryBtn()
                }
            }
        }
        viewModel.currentIdx.observe(this, fragmentIdxObserver)

        val productCountObserver = Observer<Int>{ liveData ->
            binding.tvCount.text = liveData.toString()
            binding.tvAddPrice.text = getString(R.string.form_price_won, (liveData * viewModel.productDetailInfo.price))
        }
        viewModel.itemCount.observe(this, productCountObserver)

        val addToCartObserver = Observer<Int>{ liveData ->
            if (liveData == 200){
                clearBottomButtonState()
                binding.btnShoppingbasket.applyChange()
            }
        }
        viewModel.addCartResult.observe(this, addToCartObserver)

        val inStockObserver = Observer<Boolean>{ liveData ->
            if (liveData) {
                binding.btnBuy.text = getString(R.string.buy_eng)
                binding.layoutEtc.setBackgroundColor(ContextCompat.getColor(this, R.color.songil_2))
            } else {
                binding.btnBuy.text = getString(R.string.soldout_eng)
                binding.layoutEtc.setBackgroundColor(ContextCompat.getColor(this, R.color.g_3))
            }
            binding.btnFavorite.isClickable = liveData
            binding.btnShare.isClickable = liveData
        }
        viewModel.inStock.observe(this, inStockObserver)

        val likeObserver = Observer<LikeData>{liveData ->
            binding.btnFavorite.setBackgroundResource(if (liveData.isLike == "Y") R.drawable.ic_heart_base_28 else R.drawable.ic_heart_line_28)
        }
        viewModel.likeData.observe(this, likeObserver)
    }

    private fun changeFragment(targetFragment : Fragment){
        supportFragmentManager.beginTransaction().hide(currentFragment).commit()
        if (binding.layoutNested.getIsHeaderSticky()) {
            binding.layoutNested.scrollTo(0, binding.lineSticky.y.toInt())
        }
        supportFragmentManager.beginTransaction().show(targetFragment).commit()
        currentFragment = targetFragment
    }

    private fun setButton(){
        binding.btnBuy.setOnClickListener {
            if (GlobalApplication.checkIsLogin()){
                binding.btnFavorite.visibility = View.INVISIBLE
                binding.btnShare.visibility = View.INVISIBLE
                binding.btnBuy.visibility = View.INVISIBLE
                binding.btnAddToCart.visibility = View.VISIBLE
                binding.btnBuyNow.visibility = View.VISIBLE
                binding.btnAddToCart.visibility = View.VISIBLE
                showAddView()
            } else {
                callNeedLoginDialog()
            }
        }

        binding.btnFavorite.setOnClickListener {
            if (GlobalApplication.checkIsLogin()){
                viewModel.tryToggleLike()
            } else {
                callNeedLoginDialog()
            }
        }

        binding.btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, viewModel.shareMessage())
            val shareIntent = Intent.createChooser(intent, "share")
            startActivity(shareIntent)
        }

        binding.ivArrowDown.setOnClickListener {
            clearBottomButtonState()
        }

        binding.btnBuyNow.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("ORDER_CRAFTS", arrayListOf(viewModel.getOrderCraftForm()))
            startActivityHorizontal(intent)
        }

        binding.btnAddToCart.setOnClickListener {
            viewModel.tryAddToCart()
        }

        binding.layoutContract.setOnClickListener {
            if (GlobalApplication.checkIsLogin()) {
                val intent = Intent(this, InquiryActivity::class.java)
                intent.putExtra(GlobalApplication.TARGET_IDX, viewModel.productDetailInfo.craftIdx)
                intent.putExtra(GlobalApplication.TARGET_IDX_TYPE, InquiryTarget.CRAFT)
                startActivityHorizontal(intent)
            } else {
                callNeedLoginDialog()
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra(GlobalApplication.SEARCH_CATEGORY, SearchCategory.SHOP)
            startActivityHorizontal(intent)
        }
    }

    private fun setNestedScrollView(){
        binding.layoutNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (currentFragment is CraftFragmentComment && scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight){
                (currentFragment as CraftFragmentComment).updateComment()
            }
        })
    }

    private fun applyToView(){
        Glide.with(this).load(viewModel.productDetailInfo.mainImageUrl).into(binding.ivThumbnail)
        if (viewModel.productDetailInfo.artistImageUrl != null) Glide.with(this).load(viewModel.productDetailInfo.artistImageUrl).into(binding.ivProfile)
        binding.tvCraftName.text = viewModel.productDetailInfo.name
        binding.tvPrice.text = getString(R.string.form_price_won, viewModel.productDetailInfo.price)
        binding.tvShippingFee.text = viewModel.productDetailInfo.shippingFee.joinToString("\n")
        binding.tvMaterial.text = viewModel.productDetailInfo.material.joinToString(", ")
        binding.tvUsage.text = viewModel.productDetailInfo.usage.joinToString(", ")
        binding.tvMaker.text = viewModel.productDetailInfo.artistName
        binding.tvIntroduce.text = viewModel.productDetailInfo.artistIntroduction
        binding.tvReviewCount.text = getString(R.string.form_number_bracket, viewModel.productDetailInfo.totalCommentCnt)
        if (viewModel.productDetailInfo.isNew == "N") binding.tvNew.visibility = View.GONE
        binding.tvMakerCraft.text = getString(R.string.form_artist_craft, viewModel.productDetailInfo.artistName, viewModel.productDetailInfo.name)
    }

    private fun addFragments(){
        askFragment = CraftFragmentAsk(viewModel.productDetailInfo.artistImageUrl, viewModel.productDetailInfo.artistName)
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, askFragment).commit()
        supportFragmentManager.beginTransaction().hide(askFragment).commit()
        reviewFragment = CraftFragmentComment()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, reviewFragment).commit()
        supportFragmentManager.beginTransaction().hide(reviewFragment).commit()
        detailFragment = CraftFragmentDetail(viewModel.productDetailInfo)
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

    fun getToolbarHeight() : Int {
        return binding.layoutSticky.height + binding.layoutAppbar.height
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}