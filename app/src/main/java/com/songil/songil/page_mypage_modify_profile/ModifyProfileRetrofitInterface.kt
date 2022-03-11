package com.songil.songil.page_mypage_modify_profile

import com.songil.songil.config.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ModifyProfileRetrofitInterface {
    // same with SignUpRetrofitInterface's getNicknameDuplicateCheck
    @GET("auth/duplicated-check")
    suspend fun getNicknameDuplicateCheck(@Query("nickname") nickname : String) : Response<BaseResponse>

    @Multipart
    @PATCH("my-page/profile")
    suspend fun patchProfiles(@PartMap data : HashMap<String, RequestBody>?, @Part userProfile : MultipartBody.Part?) : Response<BaseResponse>
}