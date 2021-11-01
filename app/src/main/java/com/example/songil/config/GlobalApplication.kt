package com.example.songil.config

import android.app.Application
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GlobalApplication : Application() {
    companion object {
        val API_URL = "https://dev.songil.shop/"
        const val X_ACCESS_TOKEN = "x-access-token"
        lateinit var sRetrofit: Retrofit
        lateinit var globalSharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        initRetrofitInstance()
        globalSharedPreferences = applicationContext.getSharedPreferences("Songil", MODE_PRIVATE)
    }

    private fun initRetrofitInstance() {
        val client : OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor())
            .build()

        //sRetrofit = Retrofit.Builder().baseUrl(API_URL).client(client).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build()
        sRetrofit = Retrofit.Builder().baseUrl(API_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build()
    }
}