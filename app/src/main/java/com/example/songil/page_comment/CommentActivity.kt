package com.example.songil.page_comment

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.databinding.CommentActivityBinding
import com.example.songil.recycler.adapter.AddPhotoSingleAdapter
import com.example.songil.recycler.decoration.AddPhotoDecoration
import com.example.songil.recycler.rv_interface.RvPhotoView

class CommentActivity : BaseActivity<CommentActivityBinding>(R.layout.comment_activity), RvPhotoView {

    private val viewModel : CommentViewModel by lazy { ViewModelProvider(this)[CommentViewModel::class.java] }

    private val getImageFromGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        val uri = result.data?.data
        if (uri != null) {
            (binding.rvPhoto.adapter as AddPhotoSingleAdapter).addPhoto(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setEditText()
        setRecyclerView()
        setButton()
    }

    private fun setRecyclerView(){
        binding.rvPhoto.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPhoto.adapter = AddPhotoSingleAdapter(this, this)
        binding.rvPhoto.addItemDecoration(AddPhotoDecoration(this))
    }

    private fun setEditText(){
        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.tvWordCount.text = getString(R.string.form_count, s.toString().length, 300)
                viewModel.checkCommentLength()
            }

        })
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressedHorizontal()
        }
    }

    override fun photoItemClick() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        getImageFromGallery.launch(intent)
    }
}