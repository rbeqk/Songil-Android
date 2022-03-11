package com.songil.songil.page_mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.songil.songil.R
import com.songil.songil.config.BaseFragment
import com.songil.songil.config.GlobalApplication
import com.songil.songil.config.MyPageActivityType
import com.songil.songil.data.SongilUserInfo
import com.songil.songil.databinding.MypageFragmentBinding
import com.songil.songil.page_customer_center.CustomerCenterActivity
import com.songil.songil.page_main.MainActivity
import com.songil.songil.page_mybenefit.MybenefitActivity
import com.songil.songil.page_mycomment.MycommentActivity
import com.songil.songil.page_myfavorite_article.MyFavoriteArticleActivity
import com.songil.songil.page_myfavorite_craft.MyFavoriteCraftActivity
import com.songil.songil.page_mypage_about_post.MyPagePostActivity
import com.songil.songil.page_mypage_ask_history.MyPageAskActivity
import com.songil.songil.page_mypage_modify_profile.ModifyProfileActivity
import com.songil.songil.page_needlogin.NeedLoginActivity
import com.songil.songil.page_orderstatus.OrderStatusActivity
import com.songil.songil.page_setting.SettingActivity
import com.songil.songil.popup_logout.LogoutDialog
import com.songil.songil.popup_logout.popup_interface.PopupLogoutView
import com.songil.songil.popup_warning.WarningDialog

class MypageFragment : BaseFragment<MypageFragmentBinding>(MypageFragmentBinding::bind, R.layout.mypage_fragment), PopupLogoutView{

    private lateinit var viewModel: MypageViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MypageViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setObserver()
        setButton()

        viewModel.tryGetMyInfo()
    }

    private fun setObserver(){
        val loginChangeObserver = Observer<Boolean>{ isLogin ->
            binding.btnShoppingCart.applyChange()
            if (isLogin){
                viewModel.tryGetMyInfo()
            } else {
                viewModel.clearUserInfo(getString(R.string.login_slash_signup))
            }
        }
        viewModel.isLogin.observe(viewLifecycleOwner, loginChangeObserver)

        val userInfoObserver = Observer<SongilUserInfo>{ liveData ->
            binding.invalidateAll()
            if (liveData.userProfile != null)
                Glide.with(this).load(liveData.userProfile).into(binding.ivProfile)
            else
                binding.ivProfile.setImageResource(0)
        }
        viewModel.userInfo.observe(viewLifecycleOwner, userInfoObserver)

        viewModel.fetchState.observe(viewLifecycleOwner, baseNetworkErrorObserver)
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

        binding.tvbtnLikePost.setOnClickListener {
            val intent = Intent(activity as MainActivity, MyPagePostActivity::class.java)
            intent.putExtra("MyPageActivityType", MyPageActivityType.FAVORITE_POST)
            (activity as MainActivity).startActivityHorizontal(intent)
        }

        binding.tvbtnMyWrite.setOnClickListener {
            val intent = Intent(activity as MainActivity, MyPagePostActivity::class.java)
            intent.putExtra("MyPageActivityType", MyPageActivityType.MY_POST)
            (activity as MainActivity).startActivityHorizontal(intent)
        }

        binding.tvbtnCommentPost.setOnClickListener {
            val intent = Intent(activity as MainActivity, MyPagePostActivity::class.java)
            intent.putExtra("MyPageActivityType", MyPageActivityType.COMMENT_POST)
            (activity as MainActivity).startActivityHorizontal(intent)
        }

        binding.btnBenefit.setOnClickListener {
            if (GlobalApplication.checkIsLogin()) {
                (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, MybenefitActivity::class.java))
            } else {
                (activity as MainActivity).callNeedLoginDialog()
            }
        }

        binding.tvbtnLogout.setOnClickListener {
            val logoutConfirmDialog = LogoutDialog(this)
            logoutConfirmDialog.show(childFragmentManager, logoutConfirmDialog.tag)
        }

        binding.btnFavoriteProduct.setOnClickListener {
            if (GlobalApplication.checkIsLogin()) {
                (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, MyFavoriteCraftActivity::class.java))
            } else {
                (activity as MainActivity).callNeedLoginDialog()
            }
        }

        binding.btnOrderStatus.setOnClickListener {
            if (GlobalApplication.checkIsLogin()) {
                (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, OrderStatusActivity::class.java))
            } else {
                (activity as MainActivity).callNeedLoginDialog()
            }
        }

        binding.btnMyComment.setOnClickListener {
            if (GlobalApplication.checkIsLogin()) {
                (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, MycommentActivity::class.java))
            } else {
                (activity as MainActivity).callNeedLoginDialog()
            }
        }

        binding.btnOneByOneAsk.setOnClickListener {
            if (GlobalApplication.checkIsLogin()){
                (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, MyPageAskActivity::class.java))
            } else {
                (activity as MainActivity).callNeedLoginDialog()
            }
        }

        binding.btnSetting.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, SettingActivity::class.java))
        }

        binding.tvbtnChangeToArtistPage.setOnClickListener {
            if (GlobalApplication.checkIsArtist()){
                (activity as MainActivity).toggleMyPage(true)
            } else {
                val dialogFragment = WarningDialog(getString(R.string.warning_title_artist_page), getString(R.string.warning_message_artist_page)){}
                dialogFragment.show(childFragmentManager, dialogFragment.tag)
            }
        }

        binding.tvbtnLikeArticle.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, MyFavoriteArticleActivity::class.java))
        }

        binding.tvbtnSetProfile.setOnClickListener {
            val intent = Intent(activity as MainActivity, ModifyProfileActivity::class.java)
            intent.putExtra("userNickname", viewModel.userInfo.value!!.userName)
            intent.putExtra("imageUrl", viewModel.userInfo.value!!.userProfile)
            (activity as MainActivity).startActivityHorizontal(intent)
        }

        binding.tvbtnCustomerCenter.setOnClickListener {
            (activity as MainActivity).startActivityHorizontal(Intent(activity as MainActivity, CustomerCenterActivity::class.java))
        }
    }

    override fun logout() {
        viewModel.tryLogout()
    }
}