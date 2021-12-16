package com.example.songil.page_shop.shop_category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ShopActivityCategoryBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.popup_sort.SortBottomSheet
import com.example.songil.popup_sort.popup_interface.PopupSortView
import com.example.songil.recycler.adapter.ShopCategoryTextAdapter
import com.example.songil.recycler.adapter.Craft1Adapter
import com.example.songil.recycler.adapter.Craft2Adapter
import com.example.songil.recycler.decoration.Craft1Decoration
import com.example.songil.recycler.decoration.ShopCategoryTextDecoration
import com.example.songil.recycler.decoration.Craft2Decoration
import com.example.songil.recycler.rv_interface.RvCategoryView
import com.example.songil.recycler.rv_interface.RvCraftLikeView
import com.example.songil.recycler.rv_interface.RvClickView

// RvCategoryView -> 카테고리 recyclerView 에서 카테고리 선택시 호출할 함수 정의
// PopupSortView -> sort dialog 에서 sort 기준 클릭시 호출할 함수 정의
// RvCraftView -> 이번주 인기 공예 recyclerView 에서 아이템 클릭시 호출할 함수 정의
// RvCraftLikeView -> 전체 상품을 표시하는 recyclerView 에서 아이템 클릭시, 그리고 좋아요 클릭시 호출할 함수 정의
class ShopActivityCategory : BaseActivity<ShopActivityCategoryBinding>(R.layout.shop_activity_category),
    RvCategoryView<String>, PopupSortView, RvClickView, RvCraftLikeView<Int> {

    private lateinit var viewModel : ShopCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopCategoryViewModel::class.java]

        binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvCategory.adapter = ShopCategoryTextAdapter(this, this)
        binding.rvCategory.addItemDecoration(ShopCategoryTextDecoration(this))

        binding.rvPopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.adapter = Craft2Adapter(this, this)
        binding.rvPopular.addItemDecoration(Craft2Decoration(this))

        binding.rvCraft.layoutManager = GridLayoutManager(this, 2)
        binding.rvCraft.adapter = Craft1Adapter(this, this)
        binding.rvCraft.addItemDecoration(Craft1Decoration(this))

        binding.btnSort.setOnClickListener {
            val dialogFragment = SortBottomSheet(this, viewModel.sort.value!!)
            dialogFragment.show(supportFragmentManager, dialogFragment.tag)
        }

        binding.btnShopCategory.setOnClickListener {
            if (binding.rvCategory.visibility != View.VISIBLE){
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
            } else {
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
        }
        binding.categoryBackground.setOnClickListener {
            Log.d("background", "onclick!")
        }


        // observer 세팅
        setObserver()

        setNestedScrollView()

        // liveData 변경
        val intent = intent
        viewModel.setCategory(intent.getStringExtra("category") ?: "도자공예")

        viewModel.tryGetPopular()
        viewModel.tryGetProduct()

    }

    // recyclerview 에서 실행될 함수
    override fun categoryClick(data: String) {
        viewModel.setCategory(data)

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

        (binding.rvCraft.adapter as Craft1Adapter).clearData()
        viewModel.nextPage = 10
        viewModel.tryGetProduct()
        viewModel.tryGetPopular()
    }

    // popup 에서 실행될 함수
    override fun sort(sort: String) {
        (binding.rvCraft.adapter as Craft1Adapter).clearData()
        viewModel.nextPage = 10
        viewModel.changeSort(sort)
        viewModel.tryGetProduct()
    }

    override fun itemClick(idx: Int) {
        val intent = Intent(this, CraftActivity::class.java)
        intent.putExtra(GlobalApplication.CRAFT_IDX, idx)
        startActivity(intent)
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

        val totalObserver = Observer<Int> { liveData ->
            when (liveData){
                200 -> {
                    (binding.rvCraft.adapter as Craft1Adapter).updateList(viewModel.totalCraftData)
                }
                else -> {

                }
            }
        }
        viewModel.allCraftResultCode.observe(this, totalObserver)
    }

    private fun setNestedScrollView(){
        binding.layoutNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight -  v.measuredHeight){
                viewModel.tryGetProduct()
            }
        })
    }

    // 이걸로 바꿀예정
    override fun clickData(dataKey: Int) {
        // data 클릭했을 때 해당 activity 로 이동
        val intent = Intent(this, CraftActivity::class.java)
        intent.putExtra(GlobalApplication.CRAFT_IDX, dataKey)
        startActivity(intent)
    }

    override fun clickLike(dataKey: Int, position: Int) {
        // data 의 like 버튼을 클릭했을 때, like api 호출
    }
}