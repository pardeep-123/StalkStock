package com.stalkstock.commercial.view.model

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