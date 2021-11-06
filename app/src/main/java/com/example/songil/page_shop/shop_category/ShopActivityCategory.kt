package com.example.songil.page_shop.shop_category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ShopActivityCategoryBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.page_shop.shop_category.models.CraftDetail
import com.example.songil.page_shop.shop_category.models.CraftSimple
import com.example.songil.popup_sort.SortBottomSheet
import com.example.songil.popup_sort.popup_interface.PopupSortView
import com.example.songil.recycler.adapter.ShopRvCategoryTextAdapter
import com.example.songil.recycler.adapter.ShopRvCraftAdapter
import com.example.songil.recycler.adapter.ShopRvPopularAdapter
import com.example.songil.recycler.decoration.ShopRvCategoryTextItemDecoration
import com.example.songil.recycler.decoration.ShopRvCraftDecoration
import com.example.songil.recycler.decoration.ShopRvPopularDecoration
import com.example.songil.recycler.rv_interface.RvCategoryView
import com.example.songil.recycler.rv_interface.RvCraftView

// 인터페이스 3개... 이래도 되나?
class ShopActivityCategory : BaseActivity<ShopActivityCategoryBinding>(R.layout.shop_activity_category),
    RvCategoryView<String>, PopupSortView, RvCraftView {

    private lateinit var viewModel : ShopCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopCategoryViewModel::class.java]

        binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvCategory.adapter = ShopRvCategoryTextAdapter(this, this)
        binding.rvCategory.addItemDecoration(ShopRvCategoryTextItemDecoration(this))

        binding.rvPopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.adapter = ShopRvPopularAdapter(this, this)
        binding.rvPopular.addItemDecoration(ShopRvPopularDecoration(this))

        binding.rvCraft.layoutManager = GridLayoutManager(this, 2)
        binding.rvCraft.adapter = ShopRvCraftAdapter(this, this)
        binding.rvCraft.addItemDecoration(ShopRvCraftDecoration(this))

        binding.btnSort.setOnClickListener {
            val dialogFragment = SortBottomSheet(this, viewModel.normalSort)
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

        // liveData 변경
        val intent = intent
        viewModel.setCategory(intent.getStringExtra("category") ?: "도자공예")



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
    }

    // popup 에서 실행될 함수
    override fun sort(sort: String, originalWord : String) {
        binding.tvSort.text = originalWord
        viewModel.changeSort(sort)
    }

    override fun craftClick(craftIdx: Int) {
        //Log.d("craftIdx is", craftIdx.toString())
        val intent = Intent(this, CraftActivity::class.java)
        intent.putExtra(GlobalApplication.CRAFT_IDX, craftIdx)
        startActivity(intent)
    }

    private fun setObserver(){
        val categoryObserver = Observer<String> {liveData ->
            binding.tvCategory.text = liveData
            binding.tvThisWeekPopular.text = getString(R.string.form_this_week_popular, liveData)
            (binding.rvCategory.adapter as ShopRvCategoryTextAdapter).changeCurrentCategory(liveData)
            viewModel.requestProductAll()
        }
        viewModel.category.observe(this, categoryObserver)

        val simpleCraftObserver = Observer<ArrayList<CraftSimple>> { liveData ->
            (binding.rvPopular.adapter as ShopRvPopularAdapter).applyData(liveData)
        }
        viewModel.popularCrafts.observe(this, simpleCraftObserver)

        val detailCraftObserver = Observer<ArrayList<CraftDetail>> { liveData ->
            (binding.rvCraft.adapter as ShopRvCraftAdapter).applyData(liveData)
            binding.tvSearchResult.text = getString(R.string.form_search_result, liveData.size)
        }
        viewModel.normalCrafts.observe(this, detailCraftObserver)

        val sortObserver = Observer<String> {
            viewModel.requestProductAll2()
        }
        viewModel.sort.observe(this, sortObserver)
    }
}