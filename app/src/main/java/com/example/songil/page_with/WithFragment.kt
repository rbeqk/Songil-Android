package com.example.songil.page_with

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.WithFragmentMainBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.page_with.with_abtest.WithFragmentAbtest
import com.example.songil.page_with.with_qna.WithFragmentQna
import com.example.songil.page_with.with_story.WithFragmentStory
import com.example.songil.popup_sort.SortBottomSheetSimple
import com.example.songil.popup_sort.popup_interface.PopupSortView
import com.example.songil.recycler.adapter.HotTalkAdapter
import com.example.songil.recycler.decoration.HotTalkDecoration

class WithFragment : BaseFragment<WithFragmentMainBinding>(WithFragmentMainBinding::bind, R.layout.with_fragment_main), PopupSortView {

    private val storyFragment : WithFragmentStory by lazy { WithFragmentStory() }
    private val qnaFragment : WithFragmentQna by lazy { WithFragmentQna() }
    private val abtestFragment : WithFragmentAbtest by lazy { WithFragmentAbtest() }
    private val viewModel : WithViewModel by lazy { ViewModelProvider(this)[WithViewModel::class.java] }
    private lateinit var currentFragment : Fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
        setFragment()
        setRecyclerView()
        setObserver()

        viewModel.tryGetHotTalk()
    }

    private fun setFragment(){
        childFragmentManager.beginTransaction().add(binding.layoutFragment.id, storyFragment).commit()
        currentFragment = storyFragment
    }

    private fun setButton(){
        binding.btnSort.setOnClickListener {
            val dialog = SortBottomSheetSimple(this)
            dialog.show(childFragmentManager, dialog.tag)
        }

        binding.btnStory.setOnClickListener { changeFragment(0) }
        binding.btnQna.setOnClickListener { changeFragment(1) }
        binding.btnAbTest.setOnClickListener { changeFragment(2) }
    }

    private fun setRecyclerView(){
        binding.rvHotTalk.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHotTalk.adapter = HotTalkAdapter()
        binding.rvHotTalk.addItemDecoration(HotTalkDecoration(activity as MainActivity))
    }

    private fun setObserver(){
        val hotTalkObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvHotTalk.adapter as HotTalkAdapter).applyData(viewModel.hotTalkData)
                }
            }
        }
        viewModel.loadHotTalkResult.observe(viewLifecycleOwner, hotTalkObserver)
    }

    override fun sort(sort: String) {

    }

    private fun changeFragment(idx : Int){
        when (idx){
            0 -> {
                if (currentFragment !is WithFragmentStory) {
                    binding.layoutHotTalk.visibility = View.VISIBLE
                    binding.lineSelectStory.visibility = View.VISIBLE
                    binding.lineSelectQna.visibility = View.INVISIBLE
                    binding.lineSelectAbTest.visibility = View.INVISIBLE
                    childFragmentManager.beginTransaction().replace(binding.layoutFragment.id, storyFragment).commit()
                    currentFragment = storyFragment
                }
            }
            1 -> {
                if (currentFragment !is WithFragmentQna) {
                    binding.layoutHotTalk.visibility = View.VISIBLE
                    binding.lineSelectStory.visibility = View.INVISIBLE
                    binding.lineSelectQna.visibility = View.VISIBLE
                    binding.lineSelectAbTest.visibility = View.INVISIBLE
                    childFragmentManager.beginTransaction().replace(binding.layoutFragment.id, qnaFragment).commit()
                    currentFragment = qnaFragment
                }
            }
            2 -> {
                if (currentFragment !is WithFragmentAbtest) {
                    binding.layoutHotTalk.visibility = View.GONE
                    binding.lineSelectStory.visibility = View.INVISIBLE
                    binding.lineSelectQna.visibility = View.INVISIBLE
                    binding.lineSelectAbTest.visibility = View.VISIBLE
                    childFragmentManager.beginTransaction().replace(binding.layoutFragment.id, abtestFragment).commit()
                    currentFragment = abtestFragment
                }
            }
        }
    }

}