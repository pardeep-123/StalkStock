package com.stalkstock.commercial.view.model

data class BidingListResponse(
    val body: List<BodyX>,
    val code: Int,
    val message: String,
    val success: Boolean
){
    data class BodyX(
        val bidCount: Int,
        val createdAt: String,
        val id: Int,
        val instruction: String,
        val requestNo: String,
        val userId: Int,
        val vendorId: Int
    )
}