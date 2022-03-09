package com.example.songil.page_artist.subpage_article

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SimpleRecyclerviewFragmentBinding
import com.example.songil.page_artist.ArtistActivity
import com.example.songil.page_artist.ArtistSubpageFragment
import com.example.songil.recycler.adapter.ArticleAdapter
import com.example.songil.recycler.decoration.ArticleDecoration

class ArtistArticleFragment : BaseFragment<SimpleRecyclerviewFragmentBinding>(SimpleRecyclerviewFragmentBinding::bind, R.layout.simple_recyclerview_fragment), ArtistSubpageFragment {

    private val viewModel : ArtistArticleViewModel by lazy { ViewModelProvider(this)[ArtistArticleViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setObserver()

        binding.viewEmpty.tvEmptyTarget.text = getString(R.string.empty_registered_article)

        viewModel.setArtistIdx((activity as ArtistActivity).intent.getIntExtra("artistIdx", 1))
        viewModel.tryGetArticlePageCnt()
    }

    private fun setObserver(){
        val pageResult = Observer<Int>{liveData ->
            if (liveData > 0){
                binding.viewEmpty.root.visibility = View.GONE
                viewModel.tryGetArticleList()
                if (liveData < 2){
                    binding.layoutMain.minHeight = getWindowSize()[1] - (activity as ArtistActivity).getToolbarHeight() - getStatusBarHeight()
                }
            } else {
                binding.viewEmpty.root.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.layoutMain.minHeight = getWindowSize()[1] - (activity as ArtistActivity).getToolbarHeight() - getStatusBarHeight()
            }
        }
        viewModel.articlePageCnt.observe(viewLifecycleOwner, pageResult)

        val articleResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    if (viewModel.getCurrentPage() == 0){
                        binding.progressBar.visibility = View.GONE
                    }
                    (binding.rvContent.adapter as ArticleAdapter).updateData(viewModel.newSize)
                }
            }
        }
        viewModel.articleListResult.observe(viewLifecycleOwner, articleResult)
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = ArticleAdapter(viewModel.articleList)
        binding.rvContent.addItemDecoration(ArticleDecoration(requireContext()))
    }

    override fun changeSort(sort: String) {
        viewModel.articleList.clear()
        (binding.rvContent.adapter as ArticleAdapter).notifyDataSetChanged()
        viewModel.sort = sort
        viewModel.tryGetArticlePageCnt()
    }

    override fun updateList() {
        viewModel.tryGetArticleList()
    }
}