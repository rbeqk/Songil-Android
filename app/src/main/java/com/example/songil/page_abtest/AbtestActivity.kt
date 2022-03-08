package com.example.songil.page_abtest

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.GlobalApplication
import com.example.songil.config.ReportTarget
import com.example.songil.config.WriteType
import com.example.songil.databinding.ChatActivityBinding
import com.example.songil.page_abtestwrite.AbtestWriteActivity
import com.example.songil.page_report.ReportActivity
import com.example.songil.popup_more.MoreBottomSheet
import com.example.songil.popup_more.popup_interface.PopupMoreView
import com.example.songil.popup_remove.RemoveDialog
import com.example.songil.popup_remove.popup_interface.PopupRemoveView
import com.example.songil.popup_warning.WarningDialog
import com.example.songil.recycler.adapter.PostAndChatAdapter
import com.example.songil.recycler.rv_interface.RvPostAndChatView
import com.example.songil.utils.softKeyboardCallback.KeyboardVisibilityUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Qna activity 와 99% 동일함, 나중에 리팩토링시 통합하는 작업 수행 예정
class AbtestActivity : BaseActivity<ChatActivityBinding>(R.layout.chat_activity), RvPostAndChatView, PopupMoreView, PopupRemoveView {

    private val viewModel : AbtestViewModel by lazy { ViewModelProvider(this)[AbtestViewModel::class.java] }
    private lateinit var keyboardVisibilityUtils : KeyboardVisibilityUtils
    private lateinit var writeResult : ActivityResultLauncher<Intent>
    private var itemIsExists = true // 현재 조회중인 아이템이 존재하는지 여부

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.abtestIdx = intent.getIntExtra(GlobalApplication.ABTEST_IDX, -1)

        binding.layoutRefresh.setOnRefreshListener {
            (binding.rvComment.adapter as PostAndChatAdapter).refresh()
            binding.layoutRefresh.isRefreshing = false
        }

        setRecyclerView()
        setButton()
        setObserver()

        writeResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                viewModel.tryGetAbtest()
            }
        }

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


        viewModel.tryGetAbtest()
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }

    private fun setRecyclerView(){
        binding.rvComment.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvComment.adapter = PostAndChatAdapter(this)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            itemIsExists = true
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

        binding.btnMore.setOnClickListener {
            val moreBottomSheet = MoreBottomSheet(this, (viewModel.getAbtest.isUserABTest == "Y"))
            moreBottomSheet.show(supportFragmentManager, moreBottomSheet.tag)
        }
    }

    private fun setObserver(){
        val abTestResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    binding.tvTitle.text = getString(R.string.AB_TEST_title, viewModel.getAbtest.artistName)
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                }
                2313 -> {
                    val dialog = WarningDialog("삭제된 ABTEST입니다.", "이전 페이지로 이동되며\n바로 새로고침됩니다."){
                        itemIsExists = false
                        finish()
                    }
                    dialog.show(supportFragmentManager, dialog.tag)
                }
            }
            binding.btnRegister.isClickable = true
        }
        viewModel.loadAbtest.observe(this, abTestResult)

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
        viewModel.commentResult.observe(this, commentRegisterResult)

        val deleteCommentResult = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    (binding.rvComment.adapter as PostAndChatAdapter).refresh()
                }
            }
        }
        viewModel.deleteCommentResult.observe(this, deleteCommentResult)

        val voteResult = Observer<Boolean>{ liveData ->
            if (liveData){
                viewModel.tryGetAbtest()
            }
        }
        viewModel.voteResult.observe(this, voteResult)

        val deleteObserver = Observer<Int>{ liveData ->
            when (liveData){
                200 -> {
                    finish()
                }
            }
        }
        viewModel.deleteResult.observe(this, deleteObserver)
    }

    override fun finish() {
        if (itemIsExists){
            val intent = Intent(this, BaseActivity::class.java)
            intent.putExtra("ABTEST", viewModel.getAbtest)
            setResult(RESULT_OK, intent)
        } else {
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
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra(GlobalApplication.TARGET_IDX, commentIdx)
        intent.putExtra(GlobalApplication.REPORT_TARGET, ReportTarget.ABTEST_COMMENT)
        startActivityHorizontal(intent)
    }

    override fun clickLikeBtn() { /* empty function, only qna activity use this function */ }

    override fun vote(abTestIdx: Int, vote: String) {
        if (GlobalApplication.checkIsLogin()){
            viewModel.tryVote(abTestIdx, vote)
        } else {
            callNeedLoginDialog()
        }
    }

    override fun cancelVote(abTestIdx: Int) {
        if (GlobalApplication.checkIsLogin()){
            viewModel.tryCancelVote(abTestIdx)
        } else {
            callNeedLoginDialog()
        }
    }

    override fun bottomSheetModifyClick() {
        val intent = Intent(this, AbtestWriteActivity::class.java)
        intent.putExtra(GlobalApplication.WRITE_TYPE, WriteType.MODIFY)
        intent.putExtra(GlobalApplication.TARGET_IDX, viewModel.abtestIdx)
        writeResult.launch(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }

    override fun bottomSheetRemoveClick() {
        val dialog = RemoveDialog(this)
        dialog.show(supportFragmentManager, dialog.tag)
    }

    override fun bottomSheetReportClick() {
        val intent = Intent(this, ReportActivity::class.java)
        intent.putExtra(GlobalApplication.REPORT_TARGET, ReportTarget.ABTEST)
        intent.putExtra(GlobalApplication.TARGET_IDX, viewModel.abtestIdx)
        startActivityHorizontal(intent)
    }

    override fun popupRemoveClick() {
        viewModel.tryDeleteAbTest()
    }
}