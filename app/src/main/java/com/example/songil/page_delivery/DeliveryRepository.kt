package com.example.songil.page_delivery

import com.example.songil.config.GlobalApplication
import com.example.songil.data.DeliveryStatus

class DeliveryRepository {
    private val retrofitInterface = GlobalApplication.sRetrofit.create(DeliveryRetrofitInterface::class.java)

    suspend fun getDelivery() : ArrayList<DeliveryStatus>{
        val temp = ArrayList<DeliveryStatus>()

        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "대전 HUB", "간선상차"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "성북직영", "집하처리"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "우리집", "인수자등록"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "대전 HUB", "간선상차"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "성북직영", "집하처리"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "우리집", "인수자등록"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "대전 HUB", "간선상차"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "성북직영", "집하처리"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "우리집", "인수자등록"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))
        temp.add(DeliveryStatus("2022-01-10\n22:03", "남서울 터미널", "배달 출발"))


       return temp
    }
}