package com.example.songil.page_shop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.config.GlobalApplication
import com.example.songil.data.Craft2
import com.example.songil.databinding.ShopFragmentMainBinding
import com.example.songil.page_craft.CraftActivity
import com.example.songil.page_main.MainActivity
import com.example.songil.page_shop.models.NewCraft
import com.example.songil.page_shop.models.TodayArtistsResult
import com.example.songil.page_shop.models.TodayCraft
import com.example.songil.page_shop.shop_category.ShopActivityCategory
import com.example.songil.recycler.rv_interface.RvCategoryView
import com.example.songil.recycler.adapter.ShopCategoryAdapter
import com.example.songil.recycler.adapter.ClickImageAdapter
import com.example.songil.recycler.adapter.Craft2Adapter
import com.example.songil.recycler.decoration.ShopRvCategoryItemDecoration
import com.example.songil.recycler.decoration.ShopRvNewCraftDecoration
import com.example.songil.recycler.decoration.ShopRvPopularDecoration
import com.example.songil.recycler.rv_interface.RvCraftView

class ShopFragmentMain : BaseFragment<ShopFragmentMainBinding>(ShopFragmentMainBinding::bind, R.layout.shop_fragment_main),
    RvCategoryView<String>, RvCraftView {

    private lateinit var viewModel : ShopMainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopMainViewModel::class.java]

        setRecyclerView()

        setObserver()

        viewModel.tryGetTodayArtists()
        viewModel.tryGetNewCrafts()
        viewModel.tryGetTodayCrafts()
    }

    override fun categoryClick(data: String) {
        val intent = Intent(activity as MainActivity, ShopActivityCategory::class.java)
        intent.putExtra("category", data)
        startActivity(intent)
    }

    private fun setObserver(){
        val todayArtistObserver = Observer<TodayArtistsResult>{ liveData ->
            binding.tvTodayArtisanName.text = getString(R.string.form_artist, liveData.name)
            Glide.with(activity as MainActivity).load(liveData.profileImgUrl)
            binding.tvTodayArtisanMajor.text = liveData.major
        }
        viewModel.todayArtist.observe(viewLifecycleOwner, todayArtistObserver)

        val todayCraftObserver = Observer<ArrayList<Craft2>> { liveData ->
            (binding.rvTodayCraft.adapter as Craft2Adapter).applyData(liveData)
        }
        viewModel.todayCrafts.observe(viewLifecycleOwner, todayCraftObserver)

        val newCraftObserver = Observer<ArrayList<NewCraft>> { liveData ->
            (binding.rvNewCraft.adapter as ClickImageAdapter).applyData(liveData)
        }
        viewModel.newCrafts.observe(viewLifecycleOwner, newCraftObserver)
    }

    private fun setRecyclerView(){
        binding.rvCategory.layoutManager = GridLayoutManager(activity as MainActivity, 4)
        binding.rvCategory.adapter = ShopCategoryAdapter(activity as MainActivity, this)
        binding.rvCategory.addItemDecoration(ShopRvCategoryItemDecoration(activity as MainActivity))

        binding.rvTodayCraft.layoutManager = LinearLayoutManager(activity as MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTodayCraft.adapter = Craft2Adapter(activity as MainActivity, this)
        binding.rvTodayCraft.addItemDecoration(ShopRvPopularDecoration(activity as MainActivity))   // 똑같은 간격이라 그대로 사용

        binding.rvNewCraft.layoutManager = GridLayoutManager(activity as MainActivity, 3)
        binding.rvNewCraft.adapter = ClickImageAdapter(activity as MainActivity, this)
        binding.rvNewCraft.addItemDecoration(ShopRvNewCraftDecoration(activity as MainActivity))
    }

    override fun craftClick(craftIdx: Int) {
        val intent = Intent(activity as MainActivity, CraftActivity::class.java)
        intent.putExtra(GlobalApplication.CRAFT_IDX, craftIdx)
        startActivity(intent)
    }
}