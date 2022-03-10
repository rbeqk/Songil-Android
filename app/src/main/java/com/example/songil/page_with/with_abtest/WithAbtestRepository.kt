package com.example.songil.page_with.with_abtest

import com.example.songil.config.GlobalApplication
import com.example.songil.data.ABTest

class WithAbtestRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(WithAbtestRetrofitInterface::class.java)

    suspend fun getAbTest(pageIdx : Int, sort : String) : ArrayList<ABTest>{
        val result = retrofitInterface.getAbTest("ab-test", page = pageIdx, sort = sort)
        if (result.isSuccessful && result.body()?.code == 200){
            return result.body()!!.result
        } else {
            throw UnknownError()
        }
    }

    suspend fun getAbTestPageCnt() : Int {
        val result = retrofitInterface.getAbTestPageCnt()
        if (result.isSuccessful){
            if (result.body()?.code == 200) return result.body()!!.result.totalPages
        }
        throw UnknownError()
    }
}