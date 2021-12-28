package com.example.songil.page_myfavorite

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.data.Craft1
import com.example.songil.databinding.MydetailActivityBinding
import com.example.songil.recycler.adapter.Craft1Adapter
import com.example.songil.recycler.decoration.Craft1Decoration
import com.example.songil.recycler.rv_interface.RvCraftLikeView
import com.example.songil.recycler.rv_interface.RvClickView

class MyfavoriteActivity : BaseActivity<MydetailActivityBinding>(R.layout.mydetail_activity), RvClickView, RvCraftLikeView<Int> {

    private lateinit var viewModel : MyfavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MyfavoriteViewModel::class.java]

        binding.tvTitle.text = "찜한 상품"
        setRecyclerView()
        setObserver()
        setButton()

        viewModel.getCraftData()
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = GridLayoutManager(this, 2)
        binding.rvContent.adapter = Craft1Adapter(this, this)
        binding.rvContent.addItemDecoration(Craft1Decoration(this))
    }

    private fun setObserver(){
        val craftObserver = Observer<ArrayList<Craft1>>{ liveData ->
            (binding.rvContent.adapter as Craft1Adapter).applyData(liveData)
        }
        viewModel.craftList.observe(this, craftObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressedHorizontal()
        }
    }

    override fun itemClick(idx: Int) {

    }

    // 이걸로 바꿀겁니다
    override fun clickData(dataKey: Int) {

    }

    override fun clickLike(dataKey: Int, position: Int) {

    }
}