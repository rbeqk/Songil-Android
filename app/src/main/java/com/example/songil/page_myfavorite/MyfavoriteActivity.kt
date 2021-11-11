package com.example.songil.page_myfavorite

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.MydetailActivityBinding
import com.example.songil.page_shop.shop_category.models.CraftDetail
import com.example.songil.recycler.adapter.RvCraftBaseAdapter
import com.example.songil.recycler.decoration.RvCraftBaseDecoration2
import com.example.songil.recycler.rv_interface.RvCraftView

class MyfavoriteActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity), RvCraftView {

    private lateinit var viewModel : MyfavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MyfavoriteViewModel::class.java]

        binding.tvTitle.text = "찜한 상품"
        setRecyclerView()
        setObserver()

        viewModel.getCraftData()
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = GridLayoutManager(this, 2)
        binding.rvContent.adapter = RvCraftBaseAdapter(this, this)
        binding.rvContent.addItemDecoration(RvCraftBaseDecoration2(this))
    }

    private fun setObserver(){
        val craftObserver = Observer<ArrayList<CraftDetail>>{ liveData ->
            (binding.rvContent.adapter as RvCraftBaseAdapter).applyData(liveData)
        }
        viewModel.craftList.observe(this, craftObserver)
    }

    override fun craftClick(craftIdx: Int) {

    }
}