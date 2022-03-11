package com.songil.songil.page_imagepicker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.songil.songil.R
import com.songil.songil.config.BaseActivity
import com.songil.songil.databinding.ImagepickerActivityBinding
import com.songil.songil.recycler.adapter.ImagePickerAdapter
import com.songil.songil.recycler.rv_interface.RvImagePickerView

class ImagePickerActivity : BaseActivity<ImagepickerActivityBinding>(R.layout.imagepicker_activity), RvImagePickerView {

    private var min = 0
    private var max = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        min = intent.getIntExtra("min", 0)
        max = intent.getIntExtra("max", 3)

        setButton()
        setRecyclerView()
        countCheck(0)

        getImageUri()
    }

    private fun setRecyclerView(){
        binding.rvImages.layoutManager = GridLayoutManager(this, 3)
        binding.rvImages.adapter = ImagePickerAdapter(this, min, max)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnConfirmation.setOnClickListener {
            val sendIntent = Intent(this, Activity::class.java)
            sendIntent.putExtra("imageList", (binding.rvImages.adapter as ImagePickerAdapter).getSelectImageList())
            setResult(RESULT_OK, sendIntent)
            finish()
        }
    }

    private fun getImageUri(){
        val imageList = ArrayList<ImagePickerData>()
        val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME
        )
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null)

        if (cursor != null){
            val columnIdx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while(cursor.moveToNext()){
                val id = cursor.getLong(columnIdx)
                val uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toString())
                imageList.add(ImagePickerData(uri.toString(), false))
            }
        }

        cursor?.close()
        (binding.rvImages.adapter as ImagePickerAdapter).applyData(imageList)
    }

    override fun countCheck(size: Int) {
        if (size !in min..max){
            binding.btnConfirmation.setTextColor(ContextCompat.getColor(this, R.color.g_4))
            binding.btnConfirmation.isClickable = false
        } else {
            binding.btnConfirmation.setTextColor(ContextCompat.getColor(this, R.color.songil_2))
            binding.btnConfirmation.isClickable = true
        }
    }
}