package com.songil.songil.page_delivery.models

import com.songil.songil.data.DeliveryStatus

data class DeliveryTrackingInfo(val tCode : String, val tInvoice : String, val trackingInfo : ArrayList<DeliveryStatus>)