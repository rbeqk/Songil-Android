package com.songil.songil.page_report

import com.songil.songil.config.BaseResponse
import com.songil.songil.config.GlobalApplication
import com.songil.songil.config.ReportTarget
import com.songil.songil.page_report.models.ReportData
import retrofit2.Response

class ReportRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(ReportRetrofitInterface::class.java)

    suspend fun report(target : ReportTarget, targetIdx : Int, reportData: ReportData) : Response<BaseResponse> {
        return when (target){
            ReportTarget.ABTEST -> {
                retrofitInterface.postAbtestReport(targetIdx, reportData)
            }
            ReportTarget.ABTEST_COMMENT -> {
                retrofitInterface.postAbtestCommentReport(targetIdx, reportData)
            }
            ReportTarget.ARTICLE -> {
                retrofitInterface.postArticleReport(targetIdx, reportData)
            }
            ReportTarget.ARTICLE_COMMENT -> {
                retrofitInterface.postArticleCommentReport(targetIdx, reportData)
            }
            ReportTarget.CRAFT_COMMENT -> {
                retrofitInterface.postCraftCommentReport(targetIdx, reportData)
            }
            ReportTarget.QNA -> {
                retrofitInterface.postQnAReport(targetIdx, reportData)
            }
            ReportTarget.QNA_COMMENT -> {
                retrofitInterface.postQnACommentReport(targetIdx, reportData)
            }
            ReportTarget.STORY -> {
                retrofitInterface.postStoryReport(targetIdx, reportData)
            }
            ReportTarget.STORY_COMMENT -> {
                retrofitInterface.postStoryCommentReport(targetIdx, reportData)
            }
        }
    }
}