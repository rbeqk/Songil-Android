package com.example.songil.page_artistmanage.subpage_asklist

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.data.Craft3
import com.example.songil.databinding.SimpleBaseActivityBinding
import com.example.songil.page_artistmanage.subpage_answer.ArtistManageAnswerActivity
import com.example.songil.recycler.adapter.Craft3ArtistAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArtistManageAskListActivity : BaseActivity<SimpleBaseActivityBinding>(R.layout.simple_base_activity) {

    private val viewModel : ArtistManageAskListViewModel by lazy { ViewModelProvider(this)[ArtistManageAskListViewModel::class.java] }
    private var selectPosition = 0
    private val getWriteResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == RESULT_OK){ (binding.rvContent.adapter as Craft3ArtistAdapter).applyChange(selectPosition) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvTitle.text = getString(R.string.one_by_one_ask_list)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setRecyclerView()

        setInit()

        binding.layoutRefresh.setOnRefreshListener {
            viewModel.refresh()
            (binding.rvContent.adapter as Craft3ArtistAdapter).refresh()
            binding.layoutRefresh.isRefreshing = false
        }
    }

    private fun setInit(){
        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvContent.adapter as Craft3ArtistAdapter).submitData(pagingData)
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = Craft3ArtistAdapter(Craft3ArtistComparator, ::enterWritePage)
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    object Craft3ArtistComparator : DiffUtil.ItemCallback<Craft3>() {
        override fun areItemsTheSame(oldItem: Craft3, newItem: Craft3): Boolean {
            return (oldItem == newItem)
        }

        override fun areContentsTheSame(oldItem: Craft3, newItem: Craft3): Boolean {
            return (oldItem.writable == newItem.writable)
        }

    }

    private fun enterWritePage(position : Int){
        selectPosition = position
        getWriteResult.launch(Intent(this, ArtistManageAnswerActivity::class.java))
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }


}