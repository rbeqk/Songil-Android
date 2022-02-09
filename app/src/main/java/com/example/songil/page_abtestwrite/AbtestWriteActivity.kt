package com.example.songil.page_abtestwrite

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import android.widget.DatePicker
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songil.R
import com.example.songil.config.BaseActivity
import com.example.songil.config.BaseViewModel
import com.example.songil.config.GlobalApplication
import com.example.songil.config.WriteType
import com.example.songil.databinding.AbtestActivityWriteBinding
import com.example.songil.page_imagepicker.ImagePickerActivity
import com.example.songil.recycler.adapter.AddPhotoPickerAdapter
import com.example.songil.recycler.decoration.AddPhotoDecoration
import com.example.songil.recycler.rv_interface.RvPhotoView
import com.example.songil.utils.createFileFromBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class AbtestWriteActivity : BaseActivity<AbtestActivityWriteBinding>(R.layout.abtest_activity_write), RvPhotoView {
    private val viewModel : AbtestWriteViewModel by lazy { ViewModelProvider(this)[AbtestWriteViewModel::class.java] }
    private val activity = this
    private var datePickerIsShow = false
    lateinit var imagePickerResult : ActivityResultLauncher<Intent>

    private lateinit var isNew : WriteType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        imagePickerResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val imageList = it.data?.getStringArrayListExtra("imageList")
                (binding.rvPhoto.adapter as AddPhotoPickerAdapter).applyData()
                viewModel.setImageList(imageList ?: arrayListOf())
            }
            viewModel.checkAvailable()
        }

        setObserver()
        setRecyclerView()
        setEditText()
        setButton()
        setDatePicker()

        isNew = (intent.getSerializableExtra(GlobalApplication.WRITE_TYPE) as WriteType)
        viewModel.abtestIdx = intent.getIntExtra(GlobalApplication.TARGET_IDX, -1)
        if (isNew == WriteType.MODIFY && viewModel.abtestIdx != -1){
            setModifyStat()
            (binding.rvPhoto.adapter as AddPhotoPickerAdapter).setModifyMode()
            viewModel.tryGetAbtest()
        }
    }

    // set view's state in modify mode (cannot change date, image in ab-test post)
    private fun setModifyStat(){
        binding.etYear.isClickable = false
        binding.etMonth.isClickable = false
        binding.etDay.isClickable = false
    }

    private fun setObserver(){
        val uploadResultObserver = Observer<Int>{ liveData ->
            viewModel.checkAvailable()
            dismissLoadingDialog()
            if (liveData == 200){
                viewModel.clearFiles()
                finish()
            }
        }
        viewModel.resultUpload.observe(this, uploadResultObserver)

        val getAbtestObserver = Observer<Int>{ _ ->
            binding.invalidateAll()
            (binding.rvPhoto.adapter as AddPhotoPickerAdapter).applyData()
            viewModel.checkDateWhenLoad()
        }
        viewModel.resultLoad.observe(this, getAbtestObserver)

        val dateConfirmBtnStatObserver = Observer<Boolean>{ liveData ->
            if (liveData){
                binding.tvbtnDateYes.setTextColor(ContextCompat.getColor(this, R.color.songil_2))
            } else {
                binding.tvbtnDateYes.setTextColor(ContextCompat.getColor(this, R.color.g_3))
            }
        }
        viewModel.dateConfirmBtnActivate.observe(this, dateConfirmBtnStatObserver)

        val modifyObserver = Observer<Int>{ liveData ->
            viewModel.checkAvailable()
            dismissLoadingDialog()
            when (liveData){
                200 -> {
                    val intent = Intent(this, BaseActivity::class.java)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
        viewModel.resultModify.observe(this, modifyObserver)

        val errorObserver = Observer<BaseViewModel.FetchState>{ liveData ->
            Log.d("errorHandling", "$liveData")
            viewModel.checkAvailable()
            dismissLoadingDialog()
        }
        viewModel.fetchState.observe(this, errorObserver)
    }

    private fun setDatePicker(){
        val datePickerListener =
            DatePicker.OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
                viewModel.checkDate(year, monthOfYear, dayOfMonth)
            }
        val current = Calendar.getInstance()
        binding.datePicker.init(current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH), datePickerListener)
    }

    private fun setButton(){
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.backgroundFilter.setOnClickListener {
            hideDatePicker()
        }

        binding.tvbtnDateNo.setOnClickListener {
            hideDatePicker()
        }

        binding.tvbtnDateYes.setOnClickListener {
            if (viewModel.dateConfirmBtnActivate.value == true) {
                val year = binding.datePicker.year
                val month = binding.datePicker.month
                val day = binding.datePicker.dayOfMonth
                viewModel.changeDate(year, month, day)
                binding.invalidateAll()
                hideDatePicker()
                viewModel.checkAvailable()
            }
        }

        binding.etDay.setOnClickListener {
            showDatePicker()
        }

        binding.etMonth.setOnClickListener {
            showDatePicker()
        }

        binding.etYear.setOnClickListener {
            showDatePicker()
        }

        binding.btnRegister.setOnClickListener {
            viewModel.writeBtnActivate.value = false
            showLoadingDialog()
            lifecycleScope.launch(Dispatchers.Default) {
                if (isNew == WriteType.NEW){
                    try {
                        val bitmapList = (binding.rvPhoto.adapter as AddPhotoPickerAdapter).getBitmapList()
                        val fileList = ArrayList<File>()
                        for (i in 0 until bitmapList.size){
                            val file = createFileFromBitmap(bitmapList[i], activity, i)
                            fileList.add(file)
                        }
                        viewModel.setFiles(fileList)
                        viewModel.tryUploadAbTest()
                    } catch(e : OutOfMemoryError){
                        dismissLoadingDialog()
                    }
                } else {
                    viewModel.tryModifyAbTest()
                }
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvPhoto.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPhoto.adapter = AddPhotoPickerAdapter(this, 2, viewModel.getImageUriList())
        binding.rvPhoto.addItemDecoration(AddPhotoDecoration(this))
    }

    private fun setEditText(){
        binding.etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.checkAvailable()
            }
        })
    }

    private fun showDatePicker(){
        datePickerIsShow = true

        val backgroundAnim = AlphaAnimation(0f, 1f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = true
        binding.backgroundFilter.animation = backgroundAnim
        binding.backgroundFilter.visibility = View.VISIBLE

        val anim = TranslateAnimation(0f, 0f, binding.layoutDatePicker.height.toFloat(), 0f)
        anim.duration = 350
        anim.fillAfter = true
        binding.layoutDatePicker.animation = anim
        binding.layoutDatePicker.visibility = View.VISIBLE
    }

    private fun hideDatePicker(){
        datePickerIsShow = false

        val backgroundAnim = AlphaAnimation(1f, 0f)
        backgroundAnim.duration = 350
        backgroundAnim.fillAfter = false
        binding.backgroundFilter.animation = backgroundAnim
        binding.backgroundFilter.visibility = View.GONE

        val anim = TranslateAnimation(0f, 0f, 0f, binding.layoutDatePicker.height.toFloat())
        anim.duration = 350
        anim.fillAfter = false
        binding.layoutDatePicker.animation = anim
        binding.layoutDatePicker.visibility = View.GONE
    }

    override fun finish() {
        super.finish()
        exitHorizontal
    }

    // StoryWrite의 함수와 동일
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

    override fun onBackPressed() {
        if (datePickerIsShow){
            hideDatePicker()
        } else {
            super.onBackPressed()
        }
    }

    override fun photoItemClick() {
        checkPermissionAndCall()
    }

    override fun photoItemRemove() {
        viewModel.checkAvailable()
    }
}