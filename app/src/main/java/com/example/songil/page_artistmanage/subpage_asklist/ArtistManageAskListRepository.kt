package com.example.songil.page_artistmanage.subpage_asklist

import com.example.songil.data.Craft3


class ArtistManageAskListRepository {
    fun tempGetAskList(pageIdx : Int) : ArrayList<Craft3>{
        val fromNetwork = ArrayList<Craft3>()
        return if (pageIdx == 10){
            fromNetwork.add(Craft3(1, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", "아이템${pageIdx}_1", "작가${pageIdx}", "2021.04.23", "Y"))
            fromNetwork.add(Craft3(2, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", "아이템${pageIdx}_2", "작가${pageIdx}", "2021.04.23", "Y"))
            fromNetwork
        } else {
            fromNetwork.add(Craft3(1, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", "아이템${pageIdx}_1", "작가${pageIdx}", "2021.04.23", "Y"))
            fromNetwork.add(Craft3(2, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", "아이템${pageIdx}_2", "작가${pageIdx}", "2021.04.23", "Y"))
            fromNetwork.add(Craft3(1, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", "아이템${pageIdx}_3", "작가${pageIdx}", "2021.04.23", "Y"))
            fromNetwork.add(Craft3(2, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", "아이템${pageIdx}_4", "작가${pageIdx}", "2021.04.23", "N"))
            fromNetwork.add(Craft3(1, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", "아이템${pageIdx}_5", "작가${pageIdx}", "2021.04.23", "N"))
            fromNetwork.add(Craft3(2, "https://t1.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2xEY/image/UVy3A7kOAnJVm_HaMW9vPWFyf-0.jpg", "아이템${pageIdx}_6", "작가${pageIdx}", "2021.04.23", "N"))
            fromNetwork
        }

    }
}