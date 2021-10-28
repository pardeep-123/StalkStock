package com.stalkstock.commercial.view.model

data class SendBidingRequest(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
){
    data class Body(
        val amount: String,
        val bidStatus: Int,
        val created: Int,
        val createdAt: String,
        val id: Int,
        val instruction: String,
        val requestNo: String,
        val updated: Int,
        val updatedAt: String,
        val userId: Int,
        val vendorId: Int
    )
}