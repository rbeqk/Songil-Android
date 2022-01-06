package com.example.songil.page_artistmanage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.ArtistFragmentManageBinding
import com.example.songil.page_artistmanage.subpage_asklist.ArtistManageAskListActivity
import com.example.songil.page_artistmanage.subpage_orderstat.ArtistManageOrderStatActivity
import com.example.songil.page_main.MainActivity
import com.example.songil.popup_logout.LogoutDialog
import com.example.songil.popup_logout.popup_interface.PopupLogoutView

class ArtistManageFragment : BaseFragment<ArtistFragmentManageBinding>(ArtistFragmentManageBinding::bind, R.layout.artist_fragment_manage), PopupLogoutView {

    private val viewModel : ArtistManageViewModel by lazy { ViewModelProvider(this)[ArtistManageViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
    }

    private fun setButton(){
        binding.tvbtnChangeToMypage.setOnClickListener {
            (activity as MainActivity).toggleMyPage(false)
        }

        binding.tvbtnLogout.setOnClickListener {
            val logoutConfirmDialog = LogoutDialog(this)
            logoutConfirmDialog.show(childFragmentManager, logoutConfirmDialog.tag)
        }

        binding.tvbtnOneByOneList.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity, ArtistManageAskListActivity::class.java))
        }

        binding.tvbtnOrderStatus.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity, ArtistManageOrderStatActivity::class.java))
        }
    }

    override fun logout() {
        val edit = GlobalApplication.globalSharedPreferences.edit()
        edit.remove(GlobalApplication.X_ACCESS_TOKEN).apply()
        (activity as MainActivity).toggleMyPage(false)
    }
}