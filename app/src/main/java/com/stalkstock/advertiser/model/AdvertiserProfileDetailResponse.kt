package com.stalkstock.advertiser.model

data class AdvertiserProfileDetailResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val advertiserDetail: AdvertiserDetail,
        val deviceToken: String,
        val deviceType: Int,
        val email: String,
        val id: Int,
        val mobile: String,
        val notification: String,
        val role: Int
    )

    data class AdvertiserDetail(
        val firstName: String,
        val id: Int,
        val image: String,
        val lastName: String,
        val userId: Int
    )
}