package com.stalkstock.vender.Model

data class VendorBidDetailResponse(
    val body: BidData,
    val code: Int,
    val message: String,
    val success: Boolean
)