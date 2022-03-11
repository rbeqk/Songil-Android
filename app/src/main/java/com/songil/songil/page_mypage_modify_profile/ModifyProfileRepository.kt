package com.songil.songil.page_mypage_modify_profile

import com.songil.songil.config.BaseResponse
import com.songil.songil.config.GlobalApplication
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ModifyProfileRepository {
    private val retrofit = GlobalApplication.sRetrofit.create(ModifyProfileRetrofitInterface::class.java)

    suspend fun checkNickname(nickname : String) : Response<BaseResponse> {
        val response = retrofit.getNicknameDuplicateCheck(nickname)
        if (response.isSuccessful) return response
        else throw UnknownError()
    }

    suspend fun modifyProfile(data : HashMap<String, RequestBody>?, image : MultipartBody.Part?) : Response<BaseResponse>{
        val result = retrofit.patchProfiles(data = data, userProfile = image)
        if (result.isSuccessful) return result
        else throw UnknownError()
    }
}