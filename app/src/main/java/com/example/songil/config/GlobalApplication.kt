package com.example.songil.config

import android.app.Application
import android.content.SharedPreferences
import com.example.songil.R
import com.example.songil.data.Category
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GlobalApplication : Application() {
    companion object {
        val API_URL = "https://dev.songil.shop/"
        const val X_ACCESS_TOKEN = "x-access-token"
        const val USER_IDX = "user-idx"
        const val CRAFT_IDX = "craft-idx"
        const val IS_FIRST_EXEC = "first_exec"
        lateinit var sRetrofit: Retrofit
        lateinit var globalSharedPreferences: SharedPreferences
        val categoryList = arrayListOf<Category>(
            Category("도자공예", R.drawable.ic_heart_line_28),
            Category("유리공예", R.drawable.ic_heart_line_28),
            Category("금속공예", R.drawable.ic_heart_line_28),
            Category("목공예", R.drawable.ic_heart_line_28),
            Category("섬유공예", R.drawable.ic_heart_line_28),
            Category("가죽공예", R.drawable.ic_heart_line_28),
            Category("기타공예", R.drawable.ic_heart_line_28),
            Category("전체보기", R.drawable.ic_heart_line_28),
        )
        val sort = mapOf("price" to "낮은 가격순", "popular" to "인기순", "new" to "최신순", "review" to "리뷰 많은 순")
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