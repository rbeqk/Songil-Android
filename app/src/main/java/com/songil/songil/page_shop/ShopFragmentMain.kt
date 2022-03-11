package com.songil.songil.page_shop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.config.BaseFragment
import com.songil.songil.config.GlobalApplication
import com.songil.songil.data.ClickData
import com.songil.songil.data.Craft2
import com.songil.songil.databinding.ShopFragmentMainBinding
import com.songil.songil.page_artist.ArtistActivity
import com.songil.songil.page_craft.CraftActivity
import com.songil.songil.page_main.MainActivity
import com.songil.songil.page_search.SearchActivity
import com.songil.songil.page_search.models.SearchCategory
import com.songil.songil.page_shop.models.ShopMainBanner
import com.songil.songil.page_shop.models.TodayArtistsResult
import com.songil.songil.page_shop.shop_category.ShopActivityCategory
import com.songil.songil.recycler.rv_interface.RvCategoryView
import com.songil.songil.recycler.adapter.ShopCategoryAdapter
import com.songil.songil.recycler.adapter.ClickImageAdapter
import com.songil.songil.recycler.adapter.Craft2Adapter
import com.songil.songil.recycler.decoration.ShopCategoryDecoration
import com.songil.songil.recycler.decoration.Grid3Decoration
import com.songil.songil.recycler.decoration.Craft2Decoration
import com.songil.songil.recycler.rv_interface.RvClickView
import com.songil.songil.viewPager2.adapter.Vp2BannerAdapter

class ShopFragmentMain : BaseFragment<ShopFragmentMainBinding>(ShopFragmentMainBinding::bind, R.layout.shop_fragment_main),
    RvCategoryView<Int>, RvClickView {

    private lateinit var viewModel : ShopMainViewModel
    private var isFirst = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ShopMainViewModel::class.java]

        setRecyclerView()
        setButton()
        setObserver()

        viewModel.tryGetShopMain()
    }

    override fun onResume() {
        super.onResume()
        if (!isFirst){
            binding.btnShoppingbasket.applyChange()
        } else {
            isFirst = false
        }
    }

    override fun categoryClick(data: Int) {
        val intent = Intent(activity as MainActivity, ShopActivityCategory::class.java)
        intent.putExtra("category", data)
        (activity as MainActivity).startActivityHorizontal(intent)
    }

    private fun setButton(){
        binding.ivProfile.setOnClickListener {
            val artistIdx = viewModel.todayArtist.value?.artistIdx
            if(artistIdx != null){
                val intent = Intent(activity as MainActivity, ArtistActivity::class.java)
                intent.putExtra("artistIdx", artistIdx)
                (activity as MainActivity).startActivityHorizontal(intent)
            }
        }

        binding.btnSearch.setOnClickListener {
            val intent = Intent(activity as MainActivity, SearchActivity::class.java)
            intent.putExtra(GlobalApplication.SEARCH_CATEGORY, SearchCategory.SHOP)
            (activity as MainActivity).startActivityHorizontal(intent)
        }
    }

    private fun setObserver(){
        val todayArtistObserver = Observer<TodayArtistsResult>{ liveData ->
            binding.tvTodayArtisanName.text = liveData.artistName //getString(R.string.form_artist, liveData.artistName)
            Glide.with(activity as MainActivity).load(liveData.imageUrl).into(binding.ivProfile)
            binding.tvTodayArtisanMajor.text = liveData.major
        }
        viewModel.todayArtist.observe(viewLifecycleOwner, todayArtistObserver)

        val todayCraftObserver = Observer<ArrayList<Craft2>> { liveData ->
            (binding.rvTodayCraft.adapter as Craft2Adapter).applyData(liveData)
        }
        viewModel.todayCrafts.observe(viewLifecycleOwner, todayCraftObserver)

        val newCraftObserver = Observer<ArrayList<ClickData>> { liveData ->
            (binding.rvNewCraft.adapter as ClickImageAdapter).applyData(liveData)
        }
        viewModel.newCrafts.observe(viewLifecycleOwner, newCraftObserver)

        val bannerObserver = Observer<ArrayList<ShopMainBanner>>{ liveData ->
            (binding.vp2Banner.adapter as Vp2BannerAdapter).applyData(liveData)
            binding.tvBannerCount.text = getString(R.string.form_count, 1, binding.vp2Banner.adapter?.itemCount ?: 5)
        }
        viewModel.bannerData.observe(viewLifecycleOwner, bannerObserver)

        viewModel.fetchState.observe(viewLifecycleOwner, baseNetworkErrorObserver)
    }

    private fun setRecyclerView(){
        binding.rvCategory.layoutManager = GridLayoutManager(activity as MainActivity, 4)
        binding.rvCategory.adapter = ShopCategoryAdapter(activity as MainActivity, this)
        binding.rvCategory.addItemDecoration(ShopCategoryDecoration(activity as MainActivity))

        binding.rvTodayCraft.layoutManager = LinearLayoutManager(activity as MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTodayCraft.adapter = Craft2Adapter(activity as MainActivity, this)
        binding.rvTodayCraft.addItemDecoration(Craft2Decoration(activity as MainActivity))   // 똑같은 간격이라 그대로 사용

        binding.rvNewCraft.layoutManager = GridLayoutManager(activity as MainActivity, 3)
        binding.rvNewCraft.adapter = ClickImageAdapter(activity as MainActivity)
        binding.rvNewCraft.addItemDecoration(Grid3Decoration(activity as MainActivity))

        binding.vp2Banner.adapter = Vp2BannerAdapter(activity as MainActivity)
        binding.vp2Banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvBannerCount.text = getString(R.string.form_count, position + 1, binding.vp2Banner.adapter?.itemCount ?: 5)
            }
        })
    }

    override fun itemClick(idx: Int) {
        val intent = Intent(activity as MainActivity, CraftActivity::class.java)
        intent.putExtra(GlobalApplication.CRAFT_IDX, idx)
        (activity as MainActivity).startActivityHorizontal(intent)
    }
}