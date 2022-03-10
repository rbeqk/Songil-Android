package com.example.songil.config

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.example.songil.R
import com.example.songil.popup_warning.NeedLoginDialog
import com.example.songil.utils.LoadingDialog

abstract class BaseActivity<B : ViewBinding> (@LayoutRes val layoutRes: Int) : AppCompatActivity() {
    protected lateinit var binding : B
    protected val exitVertical by lazy { overridePendingTransition(R.anim.none, R.anim.to_bottom) }
    protected val exitHorizontal by lazy { overridePendingTransition(R.anim.from_left_30, R.anim.to_right) }
    lateinit var mLoadingDialog : LoadingDialog
    // 네트워크 에러를 관찰할 observer
    protected val baseNetworkErrorObserver = Observer<BaseViewModel.FetchState>{ fetchState ->
        when (fetchState){
            BaseViewModel.FetchState.BAD_INTERNET -> {
                showSimpleToastMessage(getString(R.string.bad_internet))
            }
            BaseViewModel.FetchState.FAIL -> {
                showSimpleToastMessage(getString(R.string.bad_internet))
            }
            BaseViewModel.FetchState.WRONG_CONNECTION -> {
                showSimpleToastMessage(getString(R.string.bad_internet))
            }
            BaseViewModel.FetchState.PARSE_ERROR -> {}
            null -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
    }

    fun startActivityVertical(intent : Intent){
        startActivity(intent)
        overridePendingTransition(R.anim.from_bottom, R.anim.none)
    }

    fun startActivityHorizontal(intent : Intent){
        startActivity(intent)
        overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }

    // 로그인이 안된 경우, 호출하게 될 dialog
    fun callNeedLoginDialog(){
        val needLoginDialog = NeedLoginDialog()
        needLoginDialog.show(supportFragmentManager, needLoginDialog.tag)
    }

    fun showSimpleToastMessage(message : String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun isLogin() : Boolean = GlobalApplication.globalSharedPreferences.contains(GlobalApplication.X_ACCESS_TOKEN)

    fun showLoadingDialog(){
        mLoadingDialog = LoadingDialog(this)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog(){
        if (mLoadingDialog.isShowing){
            mLoadingDialog.dismiss()
        }
    }
}