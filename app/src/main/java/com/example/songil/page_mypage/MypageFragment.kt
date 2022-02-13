package com.example.songil.page_mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.songil.R
import com.example.songil.config.BaseFragment
import com.example.songil.config.GlobalApplication
import com.example.songil.databinding.MypageFragmentBinding
import com.example.songil.page_main.MainActivity
import com.example.songil.page_mybenefit.MybenefitActivity
import com.example.songil.page_mycomment.MycommentActivity
import com.example.songil.page_myfavorite_article.MyFavoriteArticleActivity
import com.example.songil.page_myfavorite_craft.MyFavoriteCraftActivity
import com.example.songil.page_needlogin.NeedLoginActivity
import com.example.songil.page_orderstatus.OrderstatusActivity
import com.example.songil.page_setting.SettingActivity
import com.example.songil.popup_logout.LogoutDialog
import com.example.songil.popup_logout.popup_interface.PopupLogoutView

class MypageFragment : BaseFragment<MypageFragmentBinding>(MypageFragmentBinding::bind, R.layout.mypage_fragment), PopupLogoutView{

    private lateinit var viewModel: MypageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MypageViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setObserver()
        setButton()
    }

    private fun setObserver(){
        val loginChangeObserver = Observer<Boolean>{ liveData ->
            if (liveData){
                binding.tvNickname.text = "API 연결 필요"
            } else {
                binding.tvNickname.text = "로그인해 주세요"
            }
        }
        viewModel.isLogin.observe(viewLifecycleOwner, loginChangeObserver)
    }

    override fun onResume() {
        super.onResume()

        viewModel.checkLogin()
    }

    private fun setButton(){

        binding.tvNickname.setOnClickListener {
            if (!GlobalApplication.globalSharedPreferences.contains(GlobalApplication.X_ACCESS_TOKEN)){
                (activity as MainActivity).startActivityVertical(Intent(activity as MainActivity, NeedLoginActivity::class.java))
            }
        }


        binding.btnBenefit.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, MybenefitActivity::class.java))
        }

        binding.tvbtnLogout.setOnClickListener {
            val logoutConfirmDialog = LogoutDialog(this)
            logoutConfirmDialog.show(childFragmentManager, logoutConfirmDialog.tag)
        }

        binding.btnFavoriteProduct.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, MyFavoriteCraftActivity::class.java))
        }

        binding.btnOrderStatus.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, OrderstatusActivity::class.java))
        }

        binding.btnMyComment.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, MycommentActivity::class.java))
        }

        binding.btnSetting.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, SettingActivity::class.java))
        }

        binding.tvbtnChangeToArtistPage.setOnClickListener {
            /*val dialogFragment = WarningDialog()
            dialogFragment.show(childFragmentManager, dialogFragment.tag)*/
            (activity as MainActivity).toggleMyPage(true)
        }

        binding.tvbtnLikeArticle.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, MyFavoriteArticleActivity::class.java))
        }
    }

    override fun logout() {
        viewModel.tryLogout()
    }
}