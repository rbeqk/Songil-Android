package com.example.songil.page_signup

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.SignupActivityBinding

class SignupActivity : BaseActivity<SignupActivityBinding>(R.layout.signup_activity){

    private lateinit var viewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        val fragmentObserver = Observer<Int>{ liveData ->
            when (liveData){
                -1 -> {
                    finish()
                }
                0 -> {
                    supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, SignupFragment1()).commit()
                }
                1 -> {
                    supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, SignupFragment2()).commit()
                }
                2 -> {
                    supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, SignupFragment3()).commit()
                }
                3 -> {
                    supportFragmentManager.beginTransaction().replace(binding.layoutFragment.id, SignupFragment4()).commit()
                }
                else -> {
                    finish()
                }
            }
        }
        viewModel.fragmentIdx.observe(this, fragmentObserver)
    }

    fun goToNext(){
        viewModel.setFragmentIdx(1)
    }

    fun goToPrev(){
        viewModel.setFragmentIdx(-1)
    }

}