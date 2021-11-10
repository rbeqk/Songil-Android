package com.example.songil.page_mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
//import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.MypageFragmentBinding
import com.example.songil.page_main.MainActivity

class MypaegFragment : BaseFragment<MypageFragmentBinding>(MypageFragmentBinding::bind, R.layout.mypage_fragment){

    private lateinit var viewModel: MypageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MypageViewModel::class.java]
        //binding.lifecycleOwner = this

        setObserver()
        // test
        binding.tvbtnLogout.setOnClickListener {
            viewModel.tryLogout()
        }
    }

    private fun setObserver(){
        val logoutResultObserver = Observer<Boolean>{ liveData ->
            if (liveData){
                (activity as MainActivity).moveToHome()
            }
        }
        viewModel.logoutSuccess.observe(viewLifecycleOwner, logoutResultObserver)
    }
}