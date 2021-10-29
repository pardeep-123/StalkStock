package com.stalkstock.commercial.view.model

data class BidingListResponse(
    val body: List<BodyX>,
    val code: Int,
    val message: String,
    val success: Boolean
)