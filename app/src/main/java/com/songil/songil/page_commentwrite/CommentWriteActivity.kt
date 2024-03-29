package com.songil.songil.page_commentwrite

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.CommentActivityWriteBinding
import com.songil.songil.page_imagepicker.ImagePickerActivity
import com.songil.songil.recycler.adapter.AddPhotoPickerAdapter
import com.songil.songil.recycler.decoration.AddPhotoDecoration
import com.songil.songil.recycler.rv_interface.RvPhotoView
import com.songil.songil.utils.createFileFromBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentWriteActivity : BaseActivity<CommentActivityWriteBinding>(R.layout.comment_activity_write), RvPhotoView {

    private val viewModel : CommentWriteViewModel by lazy {
        ViewModelProvider(this, CommentWriteViewModel.CommentWriteViewModelFactory(intent.getIntExtra("ORDER_DETAIL_IDX", -1)))[CommentWriteViewModel::class.java] }
    lateinit var imagePickerResult : ActivityResultLauncher<Intent>
    private val activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagePickerResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val imageList = it.data?.getStringArrayListExtra("imageList")
                (binding.rvPhoto.adapter as AddPhotoPickerAdapter).applyData(imageList ?: arrayListOf())
            }
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setEditText()
        setRecyclerView()
        setButton()
        setObserver()
    }

    private fun setRecyclerView(){
        binding.rvPhoto.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPhoto.adapter = AddPhotoPickerAdapter(this, 3, viewModel.getImageUriList())
        binding.rvPhoto.addItemDecoration(AddPhotoDecoration(this))
    }

    private fun setEditText(){
        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.tvWordCount.text = getString(R.string.form_count, s.toString().length, 500)
                viewModel.checkCommentLength()
            }
        })
    }

    private fun setObserver(){
        val uploadResultObserver = Observer<Int> { resultCode ->
            dismissLoadingDialog()
            viewModel.checkCommentLength()
            viewModel.clearFiles()
            if (resultCode == 200){
                val intent = Intent(this, BaseActivity::class.java)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        viewModel.uploadCommentResult.observe(this, uploadResultObserver)

        viewModel.fetchState.observe(this, baseNetworkErrorObserver)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.commentBtnActivate.value = false
            showLoadingDialog()
            lifecycleScope.launch(Dispatchers.Default) {
                try {
                    val bitmapList = (binding.rvPhoto.adapter as AddPhotoPickerAdapter).getBitmapList()
                    viewModel.imageFileList.clear()
                    for (i in 0 until  bitmapList.size) {
                        val file = createFileFromBitmap(bitmapList[i], activity, i)
                        viewModel.imageFileList.add(file)
                    }
                    viewModel.tryUploadComment()
                } catch (e : OutOfMemoryError) {
                    viewModel.clearFiles()
                    dismissLoadingDialog()
                }
            }
        }
    }

    private fun moveToImagePicker(){
        val intent = Intent(this, ImagePickerActivity::class.java)
        intent.putExtra("min", 1)
        intent.putExtra("max", 3)
        imagePickerResult.launch(intent)
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
        when(requestCode){
            1000 -> {
                moveToImagePicker()
            }
        }
    }

    override fun photoItemClick() {
        checkPermissionAndCall()
    }

    override fun photoItemRemove() { /* do nothing */}

    override fun finish() {
        super.finish()
        exitHorizontal
    }
}