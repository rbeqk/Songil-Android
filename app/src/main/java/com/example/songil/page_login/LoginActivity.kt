package com.example.songil.page_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.LoginActivityBinding

class LoginActivity : BaseActivity<LoginActivityBinding>(R.layout.login_activity) {

    private lateinit var viewModel : LoginViewModel
    private val fragment1 : LoginFragment1 by lazy { LoginFragment1() }
    private val fragment2 : LoginFragment2 by lazy { LoginFragment2() }
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, LoginFragment1()).commit()
        setFragments()
        setObserver()
    }

    override fun onBackPressed() {
        fragment2.stopTimer()
        super.onBackPressed()
    }

    private fun goToAuthFragment() {
        //supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, LoginFragment2()).commit()
        supportFragmentManager.beginTransaction().hide(fragment1).commit()
        supportFragmentManager.beginTransaction().show(fragment2).commit()
        fragment2.onShow()
        currentFragment = fragment2
    }

    fun backToPhoneNumberFragment() {
        //supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, LoginFragment1()).commit()
        supportFragmentManager.beginTransaction().hide(fragment2).commit()
        supportFragmentManager.beginTransaction().show(fragment1).commit()
        currentFragment = fragment1
    }

    fun finishWithResult(isLogin : Boolean = false){
        // 로그인 성공시 추가적인 작업이 있을 수 있다.
        finish()
    }

    private fun setFragments(){
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, fragment2).commit()
        supportFragmentManager.beginTransaction().hide(fragment2).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, fragment1).commit()
        currentFragment = fragment1
    }

    private fun setObserver(){
        val loginResultObserver = Observer<Int> { liveData ->
            when (liveData){
                200 -> {
                    fragment2.stopTimer()
                    finishWithResult(true)
                }
                else -> {

                }
            }
        }
        viewModel.loginResultCode.observe(this, loginResultObserver)

        val authResultCode = Observer<Int> { liveData ->
            when(liveData){
                200 -> {
                    if (currentFragment is LoginFragment1){
                        goToAuthFragment()
                    }
                    fragment2.startTimer()
                }
                else -> {

                }
            }
        }
        viewModel.authResultCode.observe(this, authResultCode)
    }
}