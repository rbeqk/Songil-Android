package com.example.songil.page_storywrite

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.StoryActivityWriteBinding
import com.example.songil.page_imagepicker.ImagePickerActivity
import com.example.songil.recycler.adapter.RvAddPhotoPickerAdapter
import com.example.songil.recycler.decoration.RvAddPhotoDecoration
import com.example.songil.recycler.rv_interface.RvPhotoView
import com.google.android.material.chip.Chip

class StoryWriteActivity : BaseActivity<StoryActivityWriteBinding>(R.layout.story_activity_write), RvPhotoView{
    private lateinit var viewModel : StoryWriteViewModel
    lateinit var imagePickerResult : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[StoryWriteViewModel::class.java]

        imagePickerResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val imageList = it.data?.getStringArrayListExtra("imageList")
                (binding.rvPhoto.adapter as RvAddPhotoPickerAdapter).applyData(imageList ?: arrayListOf())
            }
        }

        setRecyclerView()
        setEditText()
    }

    private fun setEditText(){
        binding.etTag.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                addChip(binding.etTag.text.toString())
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun addChip(word : String){
        if (word != ""){
            val chip = Chip(this)
            chip.text = word
            chip.isCloseIconVisible = true
            chip.textSize = 12f
            chip.setChipBackgroundColorResource(R.color.g_2)
            chip.isCheckable = false
            chip.setOnCloseIconClickListener {
                binding.cgTag.removeView(chip)
            }
            binding.cgTag.addView(chip)
            binding.cgTag.invalidate()
            binding.etTag.setText("")
        }
    }

    private fun setRecyclerView(){
        binding.rvPhoto.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPhoto.adapter = RvAddPhotoPickerAdapter(this, 2)
        binding.rvPhoto.addItemDecoration(RvAddPhotoDecoration(this))
    }

    override fun photoItemClick() {
        checkPermissionAndCall()
    }

    private fun checkPermissionAndCall(){
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission == PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
        } else {
            moveToImagePicker()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            1000 -> {
                moveToImagePicker()
            }
        }
    }

    private fun moveToImagePicker(){
        val intent = Intent(this, ImagePickerActivity::class.java)
        intent.putExtra("min", 2)
        intent.putExtra("max", 2)
        imagePickerResult.launch(intent)
    }
}