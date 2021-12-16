package com.example.songil.page_home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.data.ProductSimpleInfo
import com.example.songil.data.SimpleArticle
import com.example.songil.databinding.HomeFragmentBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.page_search.SearchActivity
import com.example.songil.recycler.adapter.MainTrendCraftAdapter
import com.example.songil.viewPager2.adapter.Vp2MainArticleAdapter
import com.example.songil.viewPager2.adapter.Vp2MainRecommendAdapter
import com.example.songil.recycler.decoration.MainTrendDecoration
import com.example.songil.viewPager2.decoration.Vp2MainRecommendDecoration

class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::bind, R.layout.home_fragment){

    private lateinit var viewModel: HomeViewModel

    private var isUser = false
    private var pageRangeSize = 1
    private var progressMax = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setButton()
        setScroll()
        setRecyclerView()
        setSeekBar()
        setObserver()

        viewModel.callData()
    }

    private fun setRecyclerView(){
        //binding.rvHotStory.layoutManager = GridLayoutManager(activity as MainActivity, 3)
        binding.rvTrendCraft.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrendCraft.adapter = MainTrendCraftAdapter(activity as MainActivity)
        binding.rvTrendCraft.addItemDecoration(MainTrendDecoration(activity as MainActivity))

        binding.vp2Recommend.adapter = Vp2MainRecommendAdapter(activity as MainActivity)
        binding.vp2Recommend.addItemDecoration(Vp2MainRecommendDecoration(activity as MainActivity))
        binding.vp2Recommend.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (!isUser){
                    binding.seekRecommend.setProgress(position * pageRangeSize, true)
                }
            }
        })

        binding.vp2Main.adapter = Vp2MainArticleAdapter(activity as MainActivity)
        binding.vp2Main.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.pageCountDot.changeIdx(position)
            }
        })
    }

    private fun setObserver(){
        val trendCraftObserver = Observer<ArrayList<ProductSimpleInfo>>{ liveData ->
            (binding.rvTrendCraft.adapter as MainTrendCraftAdapter).applyData(liveData)
        }
        viewModel.trendCraftData.observe(viewLifecycleOwner, trendCraftObserver)

        val recommendCraftObserver = Observer<ArrayList<ProductSimpleInfo>>{ liveData ->
            (binding.vp2Recommend.adapter as Vp2MainRecommendAdapter).applyData(liveData)
            pageRangeSize = (progressMax / (liveData.size - 1))
        }
        viewModel.recommendCraftData.observe(viewLifecycleOwner, recommendCraftObserver)

        val articleObserver = Observer<ArrayList<SimpleArticle>>{ liveData ->
            (binding.vp2Main.adapter as Vp2MainArticleAdapter).applyData(liveData)
            binding.pageCountDot.initialSetting(liveData.size)
        }
        viewModel.articleData.observe(viewLifecycleOwner, articleObserver)
    }

    private fun setSeekBar(){
        progressMax = binding.seekRecommend.max
        binding.seekRecommend.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    isUser = true
                    binding.vp2Recommend.currentItem = (seekBar!!.progress) / pageRangeSize
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isUser = false
            }
        })
    }

    private fun setScroll(){
        binding.layoutMain.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY >= binding.vp2Main.bottom){
                binding.btnSearch.setColorFilter(ContextCompat.getColor(requireContext(), R.color.songil_2))
            } else {
                binding.btnSearch.setColorFilter(ContextCompat.getColor(requireContext(), R.color.songil_1))
            }
        })
    }

    private fun setButton(){
        binding.btnSearch.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }
    }
}