package com.songil.songil.page_shop.shop_category

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.databinding.ShopActivityCategoryBinding
import com.songil.songil.page_craft.CraftActivity
import com.songil.songil.page_search.SearchActivity
import com.songil.songil.page_search.models.SearchCategory
import com.songil.songil.popup_sort.SortBottomSheet
import com.songil.songil.popup_sort.popup_interface.PopupSortView
import com.songil.songil.recycler.adapter.ShopCategoryTextAdapter
import com.songil.songil.recycler.adapter.Craft1PagingAdapter
import com.songil.songil.recycler.adapter.Craft2Adapter
import com.songil.songil.recycler.decoration.Craft1Decoration
import com.songil.songil.recycler.decoration.ShopCategoryTextDecoration
import com.songil.songil.recycler.decoration.Craft2Decoration
import com.songil.songil.recycler.rv_interface.RvCategoryView
import com.songil.songil.recycler.rv_interface.RvClickView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// RvCategoryView -> 카테고리 recyclerView 에서 카테고리 선택시 호출할 함수 정의
// PopupSortView -> sort dialog 에서 sort 기준 클릭시 호출할 함수 정의
// RvCraftView -> 이번주 인기 공예 recyclerView 에서 아이템 클릭시 호출할 함수 정의
// RvCraftLikeView -> 전체 상품을 표시하는 recyclerView 에서 아이템 클릭시, 그리고 좋아요 클릭시 호출할 함수 정의
class ShopActivityCategory : BaseActivity<ShopActivityCategoryBinding>(R.layout.shop_activity_category),
    RvCategoryView<Int>, PopupSortView, RvClickView {

    private var pagingJob : Job ?= null
    private lateinit var viewModel : ShopCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopCategoryViewModel::class.java]

        setRecyclerView()
        setButton()
        setObserver()

        val intent = intent
        viewModel.setCategory(intent.getIntExtra("category", 1))

        viewModel.tryGetPopular()

        viewModel.setInit()
        viewModel.tryGetStartIdx()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.tryGetStartIdx()
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding.btnShoppingbasket.applyChange()
    }

    // call when select category in category recyclerview
    override fun categoryClick(data: Int) {
        viewModel.setCategory(data)

        hideCategorySelectView()

        viewModel.tryGetStartIdx()

        viewModel.tryGetPopular()
    }

    // call when select sort in sort bottomSheet
    override fun sort(sort: String) {
        viewModel.tryGetStartIdx()
        viewModel.changeSort(sort)
    }

    override fun itemClick(idx: Int) {
        val intent = Intent(this, CraftActivity::class.java)
        intent.putExtra(GlobalApplication.CRAFT_IDX, idx)
        startActivity(intent)
    }

    private fun setRecyclerView(){
        binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvCategory.adapter = ShopCategoryTextAdapter(this, this)
        binding.rvCategory.addItemDecoration(ShopCategoryTextDecoration(this))

        binding.rvPopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.adapter = Craft2Adapter(this, this)
        binding.rvPopular.addItemDecoration(Craft2Decoration(this))

        binding.rvCraft.layoutManager = GridLayoutManager(this, 2)
        binding.rvCraft.adapter = Craft1PagingAdapter()
        binding.rvCraft.addItemDecoration(Craft1Decoration(this, paging = true))
    }

    private fun setObserver(){
        val categoryObserver = Observer<String> {liveData ->
            binding.tvCategory.text = liveData
            binding.tvThisWeekPopular.text = getString(R.string.form_this_week_popular, liveData)
            (binding.rvCategory.adapter as ShopCategoryTextAdapter).changeCurrentCategory(liveData)
        }
        viewModel.category.observe(this, categoryObserver)

        val sortObserver = Observer<String> { liveData ->
            binding.tvSort.text = GlobalApplication.sort[liveData]
        }
        viewModel.sort.observe(this, sortObserver)

        val startIdxObserver = Observer<Int>{ liveData ->
            lifecycleScope.launch { (binding.rvCraft.adapter as Craft1PagingAdapter).submitData( PagingData.empty()) }
            binding.layoutRefresh.isRefreshing = false
            if (liveData == 0){
                clearJob()
            } else {
                restartJob()
            }
        }
        viewModel.startIdx.observe(this, startIdxObserver)

        val popularObserver = Observer<Int>{ liveData ->
            when(liveData){
                200 -> {
                    (binding.rvPopular.adapter as Craft2Adapter).applyData(viewModel.popularCrafts)
                }
            }
        }
        viewModel.popularResultCode.observe(this, popularObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setButton(){
        binding.btnSort.setOnClickListener {
            val dialogFragment = SortBottomSheet(this, viewModel.sort.value!!)
            dialogFragment.show(supportFragmentManager, dialogFragment.tag)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnShopCategory.setOnClickListener {
            if (binding.rvCategory.visibility != View.VISIBLE){
                showCategorySelectView()
            } else {
                hideCategorySelectView()
            }
        }
        binding.categoryBackground.setOnClickListener {
            hideCategorySelectView()
        }

        binding.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra(GlobalApplication.SEARCH_CATEGORY, SearchCategory.SHOP)
            startActivityHorizontal(intent)
        }
    }

    private fun showCategorySelectView(){
        val backgroundAnim = AlphaAnimation(0f, 1f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = true
        binding.categoryBackground.animation = backgroundAnim
        binding.categoryBackground.visibility = View.VISIBLE

        val anim = TranslateAnimation(0f, 0f, -1 * binding.rvCategory.height.toFloat(), 0f)
        anim.duration = 350
        anim.fillAfter = true
        binding.rvCategory.animation = anim
        binding.rvCategory.visibility = View.VISIBLE
    }

    private fun hideCategorySelectView(){
        val backgroundAnim = AlphaAnimation(1f, 0f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = false
        binding.categoryBackground.animation = backgroundAnim
        binding.categoryBackground.visibility = View.GONE

        val anim = TranslateAnimation(0f, 0f, 0f, -1 * binding.rvCategory.height.toFloat())
        anim.duration = 350
        anim.fillAfter = false
        binding.rvCategory.animation = anim
        binding.rvCategory.visibility = View.GONE
    }

    private fun clearJob(){
        pagingJob?.cancel()
    }

    private fun restartJob(){
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvCraft.adapter as Craft1PagingAdapter).submitData(pagingData)
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}