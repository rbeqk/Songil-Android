package com.example.songil.page_delivery.models

import com.example.songil.data.DeliveryStatus

data class DeliveryTrackingInfo(val tCode : String, val tInvoice : String, val trackingInfo : ArrayList<DeliveryStatus>)