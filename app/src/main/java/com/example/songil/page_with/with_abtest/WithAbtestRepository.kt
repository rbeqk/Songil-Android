package com.example.songil.page_with.with_abtest

import com.example.songil.config.GlobalApplication
import com.example.songil.data.ABTest

class WithAbtestRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(WithAbtestRetrofitInterface::class.java)

    suspend fun getAbTest(pageIdx : Int, sort : String) : ArrayList<ABTest>{
        val result = retrofitInterface.getAbTest("ab-test", page = pageIdx, sort = sort)
        if (result.isSuccessful){
            if (result.code() == 200) return result.body()!!.result
        }
        return arrayListOf()
    }

    suspend fun getAbTestPageCnt() : Int {
        val result = retrofitInterface.getAbTestPageCnt()
        if (result.isSuccessful){
            if (result.code() == 200) return result.body()!!.result.totalPages
        }
        return -1
    }
}