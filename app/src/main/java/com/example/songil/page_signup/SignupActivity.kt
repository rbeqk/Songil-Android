package com.example.songil.page_signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.SignUpProcess
import com.example.songil.databinding.SignupActivityBinding
import com.example.songil.page_signup.models.SignUpInfo
import com.example.songil.page_signup.subpage_authcode.SignupAuthcodeFragment
import com.example.songil.page_signup.subpage_email.SignupEmailFragment
import com.example.songil.page_signup.subpage_nickname.SignupNicknameFragment
import com.example.songil.page_signup.subpage_password.SignupPasswordFragment
import com.example.songil.page_signup.subpage_password_confirm.SignupPasswordConfirmFragment
import com.example.songil.page_signup.subpage_term.SignupTermFragment

class SignupActivity : BaseActivity<SignupActivityBinding>(R.layout.signup_activity){

    private val signupInfo = SignUpInfo()
    private val termFragment : SignupTermFragment by lazy { SignupTermFragment(signupInfo) }
    private val emailFragment: SignupEmailFragment by lazy { SignupEmailFragment(signupInfo) }
    private val authcodeFragment: SignupAuthcodeFragment by lazy { SignupAuthcodeFragment(signupInfo) }
    private val passwordFragment: SignupPasswordFragment by lazy { SignupPasswordFragment(signupInfo) }
    private val passwordConfirmFragment : SignupPasswordConfirmFragment by lazy { SignupPasswordConfirmFragment(signupInfo) }
    private val nicknameFragment : SignupNicknameFragment by lazy { SignupNicknameFragment(signupInfo) }

    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this

        setFragments()
    }

    override fun finish() {
        SignupRepository.removeInstance()
        super.finish()
        exitHorizontal
    }

    private fun setFragments(){
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, nicknameFragment).commit()
        supportFragmentManager.beginTransaction().hide(nicknameFragment).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, passwordConfirmFragment).commit()
        supportFragmentManager.beginTransaction().hide(passwordConfirmFragment).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, passwordFragment).commit()
        supportFragmentManager.beginTransaction().hide(passwordFragment).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, authcodeFragment).commit()
        supportFragmentManager.beginTransaction().hide(authcodeFragment).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, emailFragment).commit()
        supportFragmentManager.beginTransaction().hide(emailFragment).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, termFragment).commit()
        currentFragment = termFragment
    }



    // call in subpage fragments
    fun changeFragment(process : SignUpProcess){
        when (process){
            SignUpProcess.CANCEL -> {
                finish()
            }
            SignUpProcess.COMPLETE -> {
                finish()
            }
            SignUpProcess.AUTH_CODE -> {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(authcodeFragment).commit()
                authcodeFragment.onShow()
                currentFragment = authcodeFragment
            }
            SignUpProcess.EMAIL -> {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(emailFragment).commit()
                emailFragment.onShow()
                currentFragment = emailFragment
            }
            SignUpProcess.NICKNAME -> {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(nicknameFragment).commit()
                nicknameFragment.onShow()
                currentFragment = nicknameFragment
            }
            SignUpProcess.PASSWORD -> {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(passwordFragment).commit()
                passwordFragment.onShow()
                currentFragment = passwordFragment
            }
            SignUpProcess.PASSWORD_CONFIRM -> {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(passwordConfirmFragment).commit()
                passwordConfirmFragment.onShow()
                currentFragment = passwordConfirmFragment
            }
            SignUpProcess.TERM -> {
                supportFragmentManager.beginTransaction().hide(currentFragment).show(termFragment).commit()
                currentFragment = termFragment
            }
        }
    }
}