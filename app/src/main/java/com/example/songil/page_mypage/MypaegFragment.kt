package com.example.songil.page_mypage

import android.os.Bundle
import android.view.View
import com.example.songil.R
import com.example.songil.config.BaseFragment
//import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.MypageFragmentBinding

class MypaegFragment : BaseFragment<MypageFragmentBinding>(MypageFragmentBinding::bind, R.layout.mypage_fragment){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // test
        /*binding.tvbtnLogout.setOnClickListener {
            val edit = GlobalApplication.globalSharedPreferences.edit()
            edit.remove(GlobalApplication.X_ACCESS_TOKEN)
            edit.putInt(GlobalApplication.USER_IDX, 0)
            edit.apply()
        }*/
    }
}