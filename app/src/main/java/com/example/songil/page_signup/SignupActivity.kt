package com.example.songil.page_signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.SignupActivityBinding

class SignupActivity : BaseActivity<SignupActivityBinding>(R.layout.signup_activity){

    private lateinit var viewModel: SignupViewModel
    private lateinit var fragment1 : SignupFragment1
    private lateinit var fragment2: SignupFragment2
    private lateinit var fragment3: SignupFragment3
    private lateinit var fragment4: SignupFragment4
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        setFragments()
        setObserver()
    }

    private fun setFragments(){
        fragment1 = SignupFragment1()
        fragment2 = SignupFragment2()
        fragment3 = SignupFragment3()
        fragment4 = SignupFragment4()

        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, fragment4).commit()
        supportFragmentManager.beginTransaction().hide(fragment4).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, fragment3).commit()
        supportFragmentManager.beginTransaction().hide(fragment3).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, fragment2).commit()
        supportFragmentManager.beginTransaction().hide(fragment2).commit()
        supportFragmentManager.beginTransaction().add(binding.layoutFragment.id, fragment1).commit()
        supportFragmentManager.beginTransaction().hide(fragment1).commit()
        currentFragment = fragment1
    }

    override fun onBackPressed() {
        fragment3.stopTimer()
        super.onBackPressed()
    }

    private fun setObserver(){
        val fragmentObserver = Observer<Int>{ liveData ->
            when (liveData){
                -1 -> { finish() }
                0 -> { changeFragment(fragment1) }
                1 -> { changeFragment(fragment2) }
                2 -> {
                    changeFragment(fragment3)
                    fragment3.fragmentShow()
                }
                3 -> { changeFragment(fragment4) }
                else -> { finish() }
            }
        }
        viewModel.fragmentIdx.observe(this, fragmentObserver)
    }

    private fun changeFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().hide(currentFragment).commit()
        supportFragmentManager.beginTransaction().show(fragment).commit()
        currentFragment = fragment
    }
}