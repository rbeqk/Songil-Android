package com.example.songil.page_artist.subpage_craft

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SimpleRecyclerviewFragmentBinding
import com.example.songil.page_artist.ArtistActivity
import com.example.songil.page_artist.ArtistSubpageFragment
import com.example.songil.recycler.adapter.Craft1Adapter
import com.example.songil.recycler.decoration.Craft1Decoration
import com.example.songil.recycler.rv_interface.RvCraftLikeView

class ArtistCraftFragment : BaseFragment<SimpleRecyclerviewFragmentBinding>(SimpleRecyclerviewFragmentBinding::bind, R.layout.simple_recyclerview_fragment), RvCraftLikeView<Int>, ArtistSubpageFragment{

    private val viewModel : ArtistCraftViewModel by lazy { ViewModelProvider(this)[ArtistCraftViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setObserver()

        viewModel.setArtistIdx((activity as ArtistActivity).intent.getIntExtra("artistIdx", 1))
        viewModel.tryGetCraftPageCnt()
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvContent.adapter = Craft1Adapter(requireContext(), this, viewModel.craftList)
        binding.rvContent.addItemDecoration(Craft1Decoration(requireContext()))
    }

    private fun setObserver(){
        val pageResult = Observer<Int>{ liveData ->
            if (liveData > 0){
                viewModel.tryGetCraftList()
                if (liveData < 2){ // 페이지 수가 적어 표시되는 아이템이 화면크기보다 작을 경우, 레이아웃의 minHeight 조정
                    binding.layoutMain.minHeight = getWindowSize()[1] - (activity as ArtistActivity).getToolbarHeight() - getStatusBarHeight()
                }
            } else {
                binding.layoutMain.minHeight = getWindowSize()[1] - (activity as ArtistActivity).getToolbarHeight() - getStatusBarHeight()
            }
        }
        viewModel.craftPageCnt.observe(viewLifecycleOwner, pageResult)

        val craftResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvContent.adapter as Craft1Adapter).updateListRef(viewModel.newSize)
                }
                else -> {

                }
            }
        }
        viewModel.craftListResult.observe(viewLifecycleOwner, craftResult)
    }

    override fun clickData(dataKey: Int) {

    }

    override fun clickLike(dataKey: Int, position: Int) {

    }

    override fun changeSort(sort: String) {
        viewModel.craftList.clear()
        (binding.rvContent.adapter as Craft1Adapter).notifyDataSetChanged()
        viewModel.sort = sort
        viewModel.tryGetCraftPageCnt()
    }

    override fun updateList() {
        viewModel.tryGetCraftList()
    }
}