package com.example.songil.page_home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.config.BaseViewModel
import com.example.songil.data.ClickData
import com.example.songil.data.CraftSimpleInfo
import com.example.songil.data.TalkWith
import com.example.songil.databinding.HomeFragmentBinding
import com.example.songil.page_home.models.HomeArticle
import com.example.songil.page_main.MainActivity
import com.example.songil.page_search.SearchActivity
import com.example.songil.popup_warning.SocketTimeoutDialog
import com.example.songil.recycler.adapter.ClickImageAdapter
import com.example.songil.recycler.adapter.MainTrendCraftAdapter
import com.example.songil.recycler.decoration.Grid3Decoration
import com.example.songil.viewPager2.adapter.Vp2MainArticleAdapter
import com.example.songil.viewPager2.adapter.Vp2MainRecommendAdapter
import com.example.songil.recycler.decoration.MainTrendDecoration
import com.example.songil.utils.dpToPx
import com.example.songil.viewPager2.adapter.Vp2MainTalkWithAdapter
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

        viewModel.tryGetHomeData()
    }

    private fun setRecyclerView(){
        binding.rvTrendCraft.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTrendCraft.adapter = MainTrendCraftAdapter(activity as MainActivity)
        binding.rvTrendCraft.addItemDecoration(MainTrendDecoration(activity as MainActivity))

        binding.vp2Recommend.adapter = Vp2MainRecommendAdapter(activity as MainActivity)
        binding.vp2Recommend.addItemDecoration(Vp2MainRecommendDecoration(activity as MainActivity))
        binding.vp2Recommend.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (!isUser){
                    binding.seekRecommend.setProgress(position * pageRangeSize, false)
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

        binding.rvHotStory.layoutManager = GridLayoutManager(activity, 3)
        binding.rvHotStory.adapter = ClickImageAdapter(activity as MainActivity, false)
        binding.rvHotStory.addItemDecoration(Grid3Decoration(activity as MainActivity))

        binding.vp2TalkWith.adapter = Vp2MainTalkWithAdapter(activity as MainActivity)
    }

    private fun setObserver(){
        val trendCraftObserver = Observer<ArrayList<CraftSimpleInfo>>{ liveData ->
            (binding.rvTrendCraft.adapter as MainTrendCraftAdapter).applyData(liveData)
        }
        viewModel.trendCraftData.observe(viewLifecycleOwner, trendCraftObserver)

        val recommendCraftObserver = Observer<ArrayList<CraftSimpleInfo>>{ liveData ->
            (binding.vp2Recommend.adapter as Vp2MainRecommendAdapter).applyData(liveData)
            pageRangeSize = (progressMax / (liveData.size - 1))
            changeThumbSize(liveData.size)
        }
        viewModel.recommendCraftData.observe(viewLifecycleOwner, recommendCraftObserver)

        val articleObserver = Observer<ArrayList<HomeArticle>>{ liveData ->
            (binding.vp2Main.adapter as Vp2MainArticleAdapter).applyData(liveData)
            binding.pageCountDot.initialSetting(liveData.size)
        }
        viewModel.articleData.observe(viewLifecycleOwner, articleObserver)

        val hotStoryObserver = Observer<ArrayList<ClickData>>{ liveData ->
            (binding.rvHotStory.adapter as ClickImageAdapter).applyData(liveData)
        }
        viewModel.hotStoryData.observe(viewLifecycleOwner, hotStoryObserver)

        val talkWithObserver = Observer<ArrayList<TalkWith>>{ liveData ->
            (binding.vp2TalkWith.adapter as Vp2MainTalkWithAdapter).applyData(liveData)
        }
        viewModel.talkWithData.observe(viewLifecycleOwner, talkWithObserver)

        viewModel.fetchState.observe(viewLifecycleOwner, baseNetworkErrorObserver)
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

    // test part
    private fun changeThumbSize(itemCount : Int){
        if (itemCount != 0) {
            val thumbWidth = (getWindowSize()[0] - dpToPx(requireContext(), 24)) / itemCount

            val thumb = ShapeDrawable(RoundRectShape(listOf(2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f).toFloatArray(), null, null))
            thumb.intrinsicHeight = dpToPx(requireContext(), 4)
            thumb.intrinsicWidth = thumbWidth
            thumb.paint.color = Color.BLACK
            binding.seekRecommend.thumb = thumb

            val seekBarParams = binding.seekRecommend.layoutParams as ConstraintLayout.LayoutParams
            seekBarParams.setMargins(thumbWidth / 2, dpToPx(requireContext(), 20),  thumbWidth / 2 , 0)
            binding.seekRecommend.layoutParams = seekBarParams
        }
    }

    private fun setScroll(){
        binding.layoutMain.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY >= binding.vp2Main.bottom){
                binding.btnSearch.setColorFilter(ContextCompat.getColor(requireContext(), R.color.songil_2))
            } else {
                binding.btnSearch.setColorFilter(ContextCompat.getColor(requireContext(), R.color.songil_1))
            }
        })
    }

    private fun setButton(){
        binding.btnSearch.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(requireContext(), SearchActivity::class.java))
        }
    }
}