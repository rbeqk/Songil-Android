package com.songil.songil.config

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {
    enum class FetchState { BAD_INTERNET, PARSE_ERROR, WRONG_CONNECTION, FAIL }

    val fetchState = MutableLiveData<FetchState>()

    protected val exceptionHandler  = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()

        when(throwable){
            is SocketTimeoutException -> fetchState.postValue(FetchState.BAD_INTERNET)
            is SocketException -> fetchState.postValue(FetchState.BAD_INTERNET)
            is HttpException -> fetchState.postValue(FetchState.PARSE_ERROR)
            is UnknownHostException -> fetchState.postValue(FetchState.WRONG_CONNECTION)
            else -> fetchState.postValue(FetchState.FAIL)
        }
    }
}