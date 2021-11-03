package com.example.songil.page_shop.shop_category

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.ShopActivityCategoryBinding
import com.example.songil.popup_sort.SortBottomSheet
import com.example.songil.popup_sort.popup_interface.PopupSortView
import com.example.songil.recycler.adapter.ShopRvCategoryTextAdapter
import com.example.songil.recycler.decoration.ShopRvCategoryTextItemDecoration
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


        binding.btnSort.setOnClickListener {
            val dialogFragment = SortBottomSheet(this)
            dialogFragment.show(supportFragmentManager, dialogFragment.tag)
        }

        binding.btnShopCategory.setOnClickListener {
            if (binding.rvCategory.visibility != View.VISIBLE){
                val anim = TranslateAnimation(0f, 0f, -1 * binding.rvCategory.height.toFloat(), 0f)
                anim.duration = 500
                anim.fillAfter = true
                binding.rvCategory.animation = anim
                binding.rvCategory.visibility = View.VISIBLE
            } else {
                val anim = TranslateAnimation(0f, 0f, 0f, -1 * binding.rvCategory.height.toFloat())
                anim.duration = 500
                anim.fillAfter = true
                binding.rvCategory.animation = anim
                binding.rvCategory.visibility = View.GONE
            }
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
    }

    // popup 에서 실행될 함수
    override fun sort(sort: String) {
        Log.d("sort", sort)
    }

    override fun craftClick(craftIdx: Int) {

    }

    private fun setObserver(){
        val categoryObserver = Observer<String> {liveData ->
            binding.tvCategory.text = liveData
            binding.tvThisWeekPopular.text = getString(R.string.form_this_week_popular, liveData)
            (binding.rvCategory.adapter as ShopRvCategoryTextAdapter).changeCurrentCategory(liveData)
            // 그리고 여기서 api 호출
        }
        viewModel.category.observe(this, categoryObserver)
    }
}