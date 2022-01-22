package com.example.songil.page_report

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.songil.config.BaseViewModel
import com.example.songil.config.ReportTarget
import com.example.songil.page_report.models.ReportData
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ReportViewModel : BaseViewModel() {
    private val repository = ReportRepository()
    var reasonIdx = MutableLiveData(-1)
    val reportButtonActivate = MutableLiveData(false)
    var reasonContent = ""
    private lateinit var reportTarget : ReportTarget
    private var idx by Delegates.notNull<Int>()

    var reportSuccess = MutableLiveData<Boolean>()

    fun setTarget(target : ReportTarget, targetIdx : Int){
        reportTarget = target
        idx = targetIdx
    }

    fun changeIdx(idx : Int){
        reasonIdx.value = idx
        checkContent()
    }

    fun checkContent(){
        reportButtonActivate.value = (reasonIdx.value!! in 0 until 6 || reasonContent.length in 1 until 301)
    }

    fun tryReport(){
        viewModelScope.launch(exceptionHandler) {
            val response = repository.report(reportTarget, idx, ReportData((reasonIdx.value!!) + 1, if (reasonContent == "" || reasonIdx.value != 6) null else reasonContent))
            if (response.isSuccessful && response.body()!!.code == 200){
                reportSuccess.postValue(true)
            } else {
                reportSuccess.postValue(false)
            }
        }
    }
}