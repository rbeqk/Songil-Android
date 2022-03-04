package com.example.songil.page_artistmanage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.databinding.ArtistFragmentManageBinding
import com.example.songil.page_artistmanage.subpage_asklist.ArtistManageAskListActivity
import com.example.songil.page_artistmanage.subpage_cancel_request.ArtistManageCancelRequestActivity
import com.example.songil.page_artistmanage.subpage_orderstat.ArtistManageOrderStatActivity
import com.example.songil.page_main.MainActivity
import com.example.songil.popup_logout.LogoutDialog
import com.example.songil.popup_logout.popup_interface.PopupLogoutView
import com.example.songil.utils.changeToPriceForm

class ArtistManageFragment : BaseFragment<ArtistFragmentManageBinding>(ArtistFragmentManageBinding::bind, R.layout.artist_fragment_manage), PopupLogoutView {

    private val viewModel : ArtistManageViewModel by lazy { ViewModelProvider(this)[ArtistManageViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
        setObserver()
    }

    // 페이지가 표시될때마다 데이터 갱신
    override fun onResume() {
        super.onResume()

        viewModel.tryGetArtistPageInfo()
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

        binding.tvbtnCancelReturn.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity, ArtistManageCancelRequestActivity::class.java))
        }
    }

    private fun setObserver(){
        val getArtistInfoObserver = Observer<Int> { resultCode ->
            when (resultCode){
                200 -> {
                    viewModel.artistPageInfo.imageUrl?.let { Glide.with(requireContext()).load(it).into(binding.ivProfile) }
                    binding.tvUserName.text = viewModel.artistPageInfo.name
                    binding.tvTotalRevenue.text = changeToPriceForm(viewModel.artistPageInfo.accumulatedSales)
                    binding.tvDayRevenue.text = changeToPriceForm(viewModel.artistPageInfo.daliySales)
                }
            }
        }
        viewModel.getArtistPageInfoResult.observe(viewLifecycleOwner, getArtistInfoObserver)
    }

    override fun logout() {
        (activity as MainActivity).toggleMyPage(false)
    }
}