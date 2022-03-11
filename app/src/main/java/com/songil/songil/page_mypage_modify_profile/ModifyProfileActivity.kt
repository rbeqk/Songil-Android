package com.songil.songil.page_mypage_modify_profile

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.ProfileModifyActivityBinding
import com.songil.songil.utils.createFileFromBitmap
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ModifyProfileActivity : BaseActivity<ProfileModifyActivityBinding>(R.layout.profile_modify_activity) {

    private val viewModel : ModifyProfileViewModel by lazy {ViewModelProvider(this)[ModifyProfileViewModel::class.java]}
    private var debounceJob : Job ?= null
    private val getImageFromGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        result.data?.data?.let {
            viewModel.activateBtn()
            Glide.with(this).asBitmap().load(it).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.ivProfile.setImageBitmap(resource)
                    bitMap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        }
    }
    private var bitMap : Bitmap ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val prevImageUrl = intent.getStringExtra("imageUrl")
        val prevUserNickname = intent.getStringExtra("userNickname")
        viewModel.prevNickname = prevUserNickname ?: ""
        prevImageUrl?.let { Glide.with(this).load(it).into(binding.ivProfile) }
        prevUserNickname?.let {binding.etNickname.setText(it)}

        setEditText()
        setButton()
        setObserver()
    }

    private fun setObserver(){
        val checkNicknameObserver = Observer<Int>{ liveData ->
            binding.cbAvailableNickname.isChecked = (liveData == 200)
            if (liveData == 200) viewModel.activateBtn()
            else viewModel.blockBtnActivate()
            when (liveData){
                200 -> { binding.tvDescription.text = getString(R.string.available_nickname) }
                2100 -> { binding.tvDescription.text = "" }
                2367 -> { binding.tvDescription.text = getString(R.string.unavailable_nickname_word_cnt) }
                3201 -> { binding.tvDescription.text = getString(R.string.unavailable_nickname_duplicate) }
                -2 -> { binding.tvDescription.text = getString(R.string.unavailable_nickname_word_form) }
            }
        }
        viewModel.nicknameCheckResult.observe(this, checkNicknameObserver)

        val modifyProfileObserver = Observer<Int>{ liveData ->
            viewModel.activateBtn()
            when(liveData){
                200 -> {
                    showSimpleToastMessage("프로필 수정에 성공했습니다.")
                    finish()
                }
                else -> {
                    showSimpleToastMessage("프로필 수정에 실패했습니다.\n잠시 후에 다시 시도해주세요.")
                }
            }
        }
        viewModel.modifyProfileResult.observe(this, modifyProfileObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tvbtnChangePhoto.setOnClickListener {
            checkPermissionAndCall()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.blockBtnActivate()
            viewModel.tryModifyProfile(if (bitMap != null) createFileFromBitmap(bitMap!!, this, 0) else null)
        }
    }

    private fun setEditText(){
        binding.etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.blockBtnActivate()
                debounceJob?.cancel()
                val text = s.toString().replace(" ", "")
                debounceJob = lifecycleScope.launch {
                    delay(300L)
                    viewModel.tryCheckNicknameCheck(text)
                }
            }
        })
    }

    private fun checkPermissionAndCall(){
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission == PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
        } else {
            callGallery()
        }
    }

    private fun callGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        getImageFromGallery.launch(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000 -> {
                callGallery()
            }
        }
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}