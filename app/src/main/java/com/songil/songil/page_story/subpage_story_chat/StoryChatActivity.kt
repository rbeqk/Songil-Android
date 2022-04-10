package com.songil.songil.page_story.subpage_story_chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.config.GlobalApplication
import com.songil.songil.config.ReportTarget
import com.songil.songil.databinding.ChatActivityBinding
import com.songil.songil.page_report.ReportActivity
import com.songil.songil.popup_block.BlockDialog
import com.songil.songil.popup_block.PopupBlockView
import com.songil.songil.recycler.adapter.PostAndChatAdapter
import com.songil.songil.recycler.rv_interface.RvPostAndChatView
import com.songil.songil.utils.softKeyboardCallback.KeyboardVisibilityUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class StoryChatActivity : BaseActivity<ChatActivityBinding>(R.layout.chat_activity), RvPostAndChatView, PopupBlockView {

    private val viewModel : StoryChatViewModel by lazy { ViewModelProvider(this)[StoryChatViewModel::class.java] }
    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils
    private var userBlock = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.layoutRefresh.setOnRefreshListener {
            (binding.rvComment.adapter as PostAndChatAdapter).refresh()
            binding.layoutRefresh.isRefreshing = false
        }

        binding.tvTitle.text = getString(R.string.chat)

        viewModel.storyIdx = intent.getIntExtra(GlobalApplication.STORY_IDX, 0)

        setRecyclerView()
        setButton()
        setObserver()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                (binding.rvComment.adapter as PostAndChatAdapter).submitData(pagingData)
            }
        }

        keyboardVisibilityUtils = KeyboardVisibilityUtils(window, onShowKeyboard = {

        }, onHideKeyboard = {
            (binding.rvComment.adapter as PostAndChatAdapter).setPointPosition(null)
            viewModel.replyPointIdx = null
            binding.etComment.hint = ""
        })

    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }

    private fun setObserver(){
        val commentRegisterResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                    binding.etComment.setText("")
                    (binding.rvComment.adapter as PostAndChatAdapter).setPointPosition(null)
                    viewModel.replyPointIdx = null
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.etComment.windowToken, 0)
                }
            }
            binding.btnRegister.isClickable = true
        }
        viewModel.registerResult.observe(this, commentRegisterResult)

        val deleteCommentResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                }
            }
        }
        viewModel.deleteCommentResult.observe(this, deleteCommentResult)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)

        val blockUserResult = Observer<Boolean> { result ->
            if (result) {
                (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                userBlock = true
            } else {
                showSimpleToastMessage("사용자 차단에 실패했습니다. 잠시 후에 시도해주세요.")
            }
        }
        viewModel.blockCommentUserResult.observe(this, blockUserResult)
    }

    private fun setRecyclerView(){
        binding.rvComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvComment.adapter = PostAndChatAdapter(this)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            if (GlobalApplication.checkIsLogin()){
                viewModel.tryWriteComment(binding.etComment.text.toString())
                binding.btnRegister.isClickable = false
            } else {
                callNeedLoginDialog()
            }
        }

        // 스토리의 채팅 부분에서는 더보기 버튼이 필요 없다.
        binding.btnMore.visibility = View.GONE
    }

    override fun finish() {
        if (userBlock){
            val intent = Intent(this, BaseActivity::class.java)
            setResult(RESULT_OK, intent)
        }
        super.finish()
        exitHorizontal
    }

    override fun clickReply(parentIdx: Int, userName : String?) {
        if (viewModel.replyPointIdx == parentIdx){
            viewModel.replyPointIdx = null
            binding.etComment.hint = ""
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etComment.windowToken, 0)
        }
        else {
            binding.etComment.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.etComment, 0)
            viewModel.replyPointIdx = parentIdx
            userName?.let { binding.etComment.hint = getString(R.string.reply_to, it) }
        }
    }

    override fun removeChat(commentIdx: Int) {
        viewModel.tryDeleteComment(commentIdx)
    }

    override fun reportChat(commentIdx: Int) {
        if (isLogin()){
            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra(GlobalApplication.TARGET_IDX, commentIdx)
            intent.putExtra(GlobalApplication.REPORT_TARGET, ReportTarget.STORY_COMMENT)
            startActivityHorizontal(intent)
        } else {
            callNeedLoginDialog()
        }
    }

    override fun blockChatUser(targetUserIdx: Int) {
        if (isLogin()){
            val dialog = BlockDialog(this, targetUserIdx)
            dialog.show(supportFragmentManager, dialog.tag)
        } else {
            callNeedLoginDialog()
        }
    }

    override fun clickLikeBtn() { /* empty function, only qna activity use this function */ }
    override fun vote(abTestIdx: Int, vote: String) { /* empty function, only ab-test activity use this function */ }
    override fun cancelVote(abTestIdx: Int) { /* empty function, only ab-test activity use this function */ }

    override fun block(targetIdx: Int?) {
        if (targetIdx != null){
            viewModel.tryBlockUserByChat(targetIdx)
        } else {
            showSimpleToastMessage("사용자 차단에 실패했습니다. 잠시 후에 시도해주세요.")
        }
    }
}