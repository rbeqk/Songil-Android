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
        const val API_URL = "https://dev.songil.shop/"
        const val X_ACCESS_TOKEN = "x-access-token"
        const val IS_ARTIST = "is_artist"
        const val CRAFT_IDX = "craft-idx"
        const val ABTEST_IDX = "abtest-idx"
        const val STORY_IDX = "story-idx"
        const val QNA_IDX = "qna-idx"
        const val ARTICLE_IDX = "article-idx"
        const val IS_FIRST_EXEC = "first_exec"
        const val REPORT_TARGET = "report_target"
        const val TARGET_IDX = "target-idx"
        const val TARGET_IDX_TYPE = "target-idx-type"
        const val WRITE_TYPE = "write-type"
        const val SEARCH_CATEGORY = "search-category"
        lateinit var sRetrofit: Retrofit
        lateinit var globalSharedPreferences: SharedPreferences
        val categoryList = arrayListOf(
            Category(1,"도자공예", R.drawable.image_ceramic_craft),
            Category(2,"유리공예", R.drawable.image_glass_craft),
            Category(3,"금속공예", R.drawable.image_metal_craft),
            Category(4,"목공예", R.drawable.image_wood_craft),
            Category(5,"섬유공예", R.drawable.image_textile_craft),
            Category(6,"가죽공예", R.drawable.image_leather_craft),
            Category(7,"기타공예", R.drawable.image_etc_craft),
            Category(8,"전체보기", R.drawable.ic_heart_line_28),
        )
        val sort = mapOf("price" to "낮은 가격순", "popular" to "인기순", "new" to "최신순", "comment" to "리뷰 많은 순")
        val courierMap = mapOf("01" to "우체국택배", "04" to "CJ대한통운", "05" to "한진택배", "06" to "로젠택배", "08" to "롯데택배", "11" to "일양로지스",
                "16" to "한의사랑택배", "17" to "천일택배", "18" to "건영택배", "20" to "한덱스",
                "22" to "대신택배", "23" to "경동택배", "24" to "GS Postbox 택배", "32" to "합동택배", "40" to "굿투럭", "43" to "애니트랙",
                "44" to "SLX택배", "45" to "호남택배", "47" to "우리한방택배", "53" to "농협택배")

        fun checkIsLogin() : Boolean {
            return globalSharedPreferences.contains(X_ACCESS_TOKEN)
        }

        fun checkIsArtist() : Boolean {
            return globalSharedPreferences.getBoolean(IS_ARTIST, false) && globalSharedPreferences.contains(X_ACCESS_TOKEN)
        }
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