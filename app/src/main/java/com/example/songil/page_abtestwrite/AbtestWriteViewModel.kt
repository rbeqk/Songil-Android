package com.example.songil.page_abtestwrite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.page_abtest.AbtestRepository
import com.example.songil.utils.toPlainRequestBody
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class AbtestWriteViewModel : BaseViewModel() {
    private val abtestWriteRepository = AbtestWriteRepository()
    private val abtestRepository by lazy { AbtestRepository() } // when write new ab-test, load abtestRepository doesn't use. so use lazy init

    var abtestIdx = -1

    var abtestContent = ""
    var year = Calendar.getInstance().get(Calendar.YEAR).toString()
    var month = (Calendar.getInstance().get(Calendar.MONTH) + 1).toString()
    var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()

    private val imageUriList = ArrayList<String>()
    private val imageFileList = ArrayList<File>()

    var writeBtnActivate = MutableLiveData(false)
    var dateConfirmBtnActivate = MutableLiveData(false)

    var resultUpload = MutableLiveData<Int>()
    var resultLoad = MutableLiveData<Int>()
    var resultModify = MutableLiveData<Int>()

    fun tryGetAbtest() {
        viewModelScope.launch (exceptionHandler){
            val result = abtestRepository.getAbtest(abtestIdx)
            val date = result.deadline.split(".")
            if (date.size == 3){
                year = date[0]
                month = date[1]
                day = date[2]
            }
            abtestContent = result.content
            imageUriList.clear()
            imageUriList.add(result.imageA)
            imageUriList.add(result.imageB)
            resultLoad.postValue(200)

        }
    }

    // in storyWriteViewModel, this function contains uploading post and modifying post,
    // but in AbtestWriteViewModel, divided into upload, modify abTest function
    fun tryUploadAbTest(){
        viewModelScope.launch(exceptionHandler) {
            val fileArray = ArrayList<MultipartBody.Part>()
            for (file in imageFileList){
                val requestBody = file.asRequestBody("image/*".toMediaType())
                fileArray.add(MultipartBody.Part.createFormData("image", file.name, requestBody))
            }
            val hashMap = hashMapOf<String, RequestBody>()

            hashMap["content"] = toPlainRequestBody(abtestContent)
            hashMap["year"] = toPlainRequestBody(year)
            hashMap["month"] = toPlainRequestBody(month)
            hashMap["day"] = toPlainRequestBody(day)

            val result = abtestWriteRepository.uploadAbTest(hashMap, fileArray)
            resultUpload.postValue(result.body()?.code ?: -1)
        }
    }

    fun tryModifyAbTest(){
        viewModelScope.launch(exceptionHandler) {
            val result = abtestWriteRepository.modifyAbTest(abtestIdx, abtestContent)
            resultModify.postValue(result.body()?.code ?: -1)
        }
    }

    fun getImageUriList() = imageUriList

    fun checkAvailable(){
        writeBtnActivate.value = ( (dateConfirmBtnActivate.value == true) && imageUriList.size == 2 && abtestContent != "")
    }

    fun checkDateWhenLoad(){
        val tempYear = year.toIntOrNull()
        val tempMonth = month.toIntOrNull()
        val tempDay = day.toIntOrNull()
        if (tempYear != null && tempMonth != null && tempDay != null){
            checkDate(tempYear, tempMonth, tempDay)
        }
    }


    fun checkDate(inputYear : Int, inputMonth : Int, inputDay : Int) {
        val inputCalendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, inputYear)
            set(Calendar.MONTH, inputMonth)
            set(Calendar.DAY_OF_MONTH, inputDay)
        }.timeInMillis
        val currentDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val available =  ((inputCalendar - currentDate) / (24*60*60*1000)) > 0
        dateConfirmBtnActivate.value = available
    }

    fun changeDate(inputYear : Int, inputMonth : Int, inputDay : Int){
        year = inputYear.toString()
        month = (inputMonth + 1).toString()
        day = inputDay.toString()
    }

    fun setFiles(fileList : ArrayList<File>){
        imageFileList.clear()
        imageFileList.addAll(fileList)
    }

    fun setImageList(newImageUriList : ArrayList<String>){
        imageUriList.clear()
        imageUriList.addAll(newImageUriList)
    }

    fun clearFiles(){
        for (file in imageFileList){
            if (file.exists())
                file.delete()
        }
    }
}