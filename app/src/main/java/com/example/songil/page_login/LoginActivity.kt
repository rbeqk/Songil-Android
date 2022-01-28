package com.example.songil.page_login

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.LoginProcess
import com.example.songil.databinding.LoginActivityBinding
import com.example.songil.page_login.models.LoginInfo
import com.example.songil.page_login.subpage_password.LoginPasswordFragment
import com.example.songil.page_login.sugpage_email.LoginEmailFragment

class LoginActivity : BaseActivity<LoginActivityBinding>(R.layout.login_activity) {

    private val loginInfo = LoginInfo()
    private val emailFragment : LoginEmailFragment by lazy { LoginEmailFragment(loginInfo) }
    private val passwordFragment : LoginPasswordFragment by lazy { LoginPasswordFragment(loginInfo) }
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragments()
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    private fun setFragments(){
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, passwordFragment).commit()
        supportFragmentManager.beginTransaction().hide(passwordFragment).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, emailFragment).commit()
        currentFragment = emailFragment
    }

    fun changeFragment(loginProcess: LoginProcess){
        when (loginProcess) {
            LoginProcess.CANCEL -> {
                finish()
            }
            LoginProcess.COMPLETE -> {
                showSimpleToastMessage(getString(R.string.toast_login_success))
                finish()
            }
            LoginProcess.EMAIL -> {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(emailFragment).commit()
                emailFragment.onShow()
                currentFragment = emailFragment
            }
            LoginProcess.PASSWORD -> {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(passwordFragment).commit()
                passwordFragment.onShow()
                currentFragment = passwordFragment
            }
        }
    }
}