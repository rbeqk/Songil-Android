package com.songil.songil.page_with

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.BaseFragment
import com.songil.songil.config.GlobalApplication
import com.songil.songil.config.WriteType
import com.songil.songil.databinding.WithFragmentMainBinding
import com.songil.songil.page_abtestwrite.AbtestWriteActivity
import com.songil.songil.page_main.MainActivity
import com.songil.songil.page_qnawrite.QnaWriteActivity
import com.songil.songil.page_search.SearchActivity
import com.songil.songil.page_search.models.SearchCategory
import com.songil.songil.page_storywrite.StoryWriteActivity
import com.songil.songil.page_with.with_abtest.WithFragmentAbtest
import com.songil.songil.page_with.with_qna.WithFragmentQna
import com.songil.songil.page_with.with_story.WithFragmentStory
import com.songil.songil.popup_sort.SortBottomSheetSimple
import com.songil.songil.popup_sort.popup_interface.PopupSortView
import com.songil.songil.recycler.adapter.HotTalkAdapter
import com.songil.songil.recycler.decoration.HotTalkDecoration

class WithFragment : BaseFragment<WithFragmentMainBinding>(WithFragmentMainBinding::bind, R.layout.with_fragment_main), PopupSortView {

    private val storyFragment : WithFragmentStory by lazy { WithFragmentStory() }
    private val qnaFragment : WithFragmentQna by lazy { WithFragmentQna() }
    private val abtestFragment : WithFragmentAbtest by lazy { WithFragmentAbtest() }
    private val viewModel : WithViewModel by lazy { ViewModelProvider(this)[WithViewModel::class.java] }
    private lateinit var currentFragment : WithSubFragmentInterface
    private lateinit var writeResult : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        writeResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                currentFragment.reload()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
        setFragment()
        setRecyclerView()
        setObserver()

        viewModel.tryGetHotTalk()
    }

    private fun setFragment(){
        childFragmentManager.beginTransaction().add(binding.layoutFragment.id, abtestFragment).hide(abtestFragment).add(binding.layoutFragment.id, qnaFragment).hide(qnaFragment).commit()
        childFragmentManager.beginTransaction().add(binding.layoutFragment.id, storyFragment).commit()
        currentFragment = storyFragment
        if (GlobalApplication.checkIsLogin()) {
            binding.btnWrite.visibility = View.VISIBLE
        } else {
            binding.btnWrite.visibility = View.GONE
        }
    }

    private fun setButton(){
        binding.btnSort.setOnClickListener {
            val dialog = SortBottomSheetSimple(this, currentFragment.getSort())
            dialog.show(childFragmentManager, dialog.tag)
        }

        binding.btnStory.setOnClickListener { changeFragment(0) }
        binding.btnQna.setOnClickListener { changeFragment(1) }
        binding.btnAbTest.setOnClickListener { changeFragment(2) }

        binding.btnWrite.setOnClickListener {
            if (!(activity as BaseActivity<*>).isLogin()){
                (activity as BaseActivity<*>).callNeedLoginDialog()
            } else {
                when (currentFragment) {
                    is WithFragmentStory -> {
                        val intent = Intent(activity as MainActivity, StoryWriteActivity::class.java)
                        intent.putExtra(GlobalApplication.WRITE_TYPE, WriteType.NEW)
                        writeResult.launch(intent)
                        (activity as MainActivity).overridePendingTransition(R.anim.from_right, R.anim.to_left)
                    }
                    is WithFragmentQna -> {
                        val intent = Intent(activity as MainActivity, QnaWriteActivity::class.java)
                        intent.putExtra(GlobalApplication.WRITE_TYPE, WriteType.NEW)
                        writeResult.launch(intent)
                        (activity as MainActivity).overridePendingTransition(R.anim.from_right, R.anim.to_left)
                    }
                    is WithFragmentAbtest -> {
                        val intent = Intent(activity as MainActivity, AbtestWriteActivity::class.java)
                        intent.putExtra(GlobalApplication.WRITE_TYPE, WriteType.NEW)
                        (activity as MainActivity).startActivityHorizontal(intent)
                    }
                    else -> {

                    }
                }
            }
        }

        binding.btnSearch.setOnClickListener {
            val intent = Intent(activity as MainActivity, SearchActivity::class.java)
            intent.putExtra(GlobalApplication.SEARCH_CATEGORY, SearchCategory.WITH)
            (activity as MainActivity).startActivityHorizontal(intent)
        }
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

        viewModel.fetchState.observe(viewLifecycleOwner, baseNetworkErrorObserver)
    }

    override fun sort(sort: String) {
        currentFragment.sort(sort)
        binding.tvSort.text = GlobalApplication.sort[sort]
    }

    private fun changeFragment(idx : Int){
        when (idx){
            0 -> {
                if (currentFragment !is WithFragmentStory) {
                    childFragmentManager.beginTransaction().hide(currentFragment as Fragment).commit()
                    binding.layoutHotTalk.visibility = View.VISIBLE
                    binding.lineSelectStory.visibility = View.VISIBLE
                    binding.lineSelectQna.visibility = View.INVISIBLE
                    binding.lineSelectAbTest.visibility = View.INVISIBLE
                    storyFragment.onShow()
                    childFragmentManager.beginTransaction().show(storyFragment).commit()
                    if (GlobalApplication.checkIsLogin()) {
                        binding.btnWrite.visibility = View.VISIBLE
                    } else {
                        binding.btnWrite.visibility = View.GONE
                    }
                    currentFragment = storyFragment
                }
            }
            1 -> {
                if (currentFragment !is WithFragmentQna) {
                    childFragmentManager.beginTransaction().hide(currentFragment as Fragment).commit()
                    binding.layoutHotTalk.visibility = View.VISIBLE
                    binding.lineSelectStory.visibility = View.INVISIBLE
                    binding.lineSelectQna.visibility = View.VISIBLE
                    binding.lineSelectAbTest.visibility = View.INVISIBLE
                    qnaFragment.onShow()
                    childFragmentManager.beginTransaction().show(qnaFragment).commit()
                    if (GlobalApplication.checkIsLogin()) {
                        binding.btnWrite.visibility = View.VISIBLE
                    } else {
                        binding.btnWrite.visibility = View.GONE
                    }
                    currentFragment = qnaFragment
                }
            }
            2 -> {
                if (currentFragment !is WithFragmentAbtest) {
                    childFragmentManager.beginTransaction().hide(currentFragment as Fragment).commit()
                    binding.layoutHotTalk.visibility = View.GONE
                    binding.lineSelectStory.visibility = View.INVISIBLE
                    binding.lineSelectQna.visibility = View.INVISIBLE
                    binding.lineSelectAbTest.visibility = View.VISIBLE
                    abtestFragment.onShow()
                    childFragmentManager.beginTransaction().show(abtestFragment).commit()
                    if (GlobalApplication.checkIsArtist()) {
                        binding.btnWrite.visibility = View.VISIBLE
                    } else {
                        binding.btnWrite.visibility = View.GONE
                    }
                    currentFragment = abtestFragment
                }
            }
        }
        binding.tvSort.text = GlobalApplication.sort[currentFragment.getSort()]
    }

}