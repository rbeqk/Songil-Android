package com.example.songil.page_storywrite

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.StoryActivityWriteBinding
import com.example.songil.page_imagepicker.ImagePickerActivity
import com.example.songil.recycler.adapter.AddPhotoPickerAdapter
import com.example.songil.recycler.decoration.AddPhotoDecoration
import com.example.songil.recycler.rv_interface.RvPhotoView
import com.google.android.material.chip.Chip

class StoryWriteActivity : BaseActivity<StoryActivityWriteBinding>(R.layout.story_activity_write), RvPhotoView{
    private lateinit var viewModel : StoryWriteViewModel
    lateinit var imagePickerResult : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[StoryWriteViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        imagePickerResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val imageList = it.data?.getStringArrayListExtra("imageList")
                (binding.rvPhoto.adapter as AddPhotoPickerAdapter).applyData(imageList ?: arrayListOf())
                viewModel.setImageList(changeUriToPath(imageList))
            }
        }

        setObserver()
        setRecyclerView()
        setEditText()
        setButton()
    }

    private fun setObserver(){
        val uploadResultObserver = Observer<Int>{ liveData ->
            if (liveData == 200){
                finish()
            }
        }
        viewModel.resultUpload.observe(this, uploadResultObserver)

        val tagEditVisibleObserver = Observer<Boolean>{ liveData ->
            if (liveData){
                binding.etTag.visibility = View.VISIBLE
            } else {
                binding.etTag.visibility = View.GONE
            }
        }
        viewModel.tagWritable.observe(this, tagEditVisibleObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setEditText(){
        binding.etTag.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || binding.cgTag.childCount < 3){
                addChip(binding.etTag.text.toString())
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
                viewModel.tagList.remove(word)
                viewModel.checkTagCount()
            }
            binding.cgTag.addView(chip)
            viewModel.tagList.add(word)
            viewModel.checkTagCount()
            binding.cgTag.invalidate()
            binding.etTag.setText("")
            if(binding.cgTag.childCount == 3){
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(binding.etTag.windowToken, 0)
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvPhoto.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPhoto.adapter = AddPhotoPickerAdapter(this, 3)
        binding.rvPhoto.addItemDecoration(AddPhotoDecoration(this))
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
        intent.putExtra("min", 1)
        intent.putExtra("max", 3)
        imagePickerResult.launch(intent)
    }

    private fun changeUriToPath(uriList : ArrayList<String>?) : ArrayList<String>{
        val pathList = ArrayList<String>()
        if (uriList == null){
            return pathList
        }

        for (uriString in uriList){
            val uri = uriString.toUri()
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.moveToNext()
            pathList.add(cursor!!.getString(cursor.getColumnIndex("_data")))
            cursor.close()
        }
        return pathList
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}