package com.example.songil.page_shop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.ShopFragmentMainBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.page_shop.shop_category.ShopActivityCategory
import com.example.songil.recycler.rv_interface.RvCategoryView
import com.example.songil.recycler.adapter.ShopRvCategoryAdapter
import com.example.songil.recycler.decoration.ShopRvCategoryItemDecoration

class ShopFragmentMain : BaseFragment<ShopFragmentMainBinding>(ShopFragmentMainBinding::bind, R.layout.shop_fragment_main),
    RvCategoryView<String> {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCategory.layoutManager = GridLayoutManager(activity as MainActivity, 4)
        binding.rvCategory.adapter = ShopRvCategoryAdapter(activity as MainActivity, this)
        binding.rvCategory.addItemDecoration(ShopRvCategoryItemDecoration(activity as MainActivity))
    }

    override fun categoryClick(data: String) {
        val intent = Intent(activity as MainActivity, ShopActivityCategory::class.java)
        intent.putExtra("category", data)
        startActivity(intent)
    }
}