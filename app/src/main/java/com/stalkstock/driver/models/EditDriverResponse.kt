package com.stalkstock.driver.models

 data class EditDriverResponse(
    val body: Edit,
    val code: Int,
    val message: String,
    val success: Boolean
)

data class Edit(
    val authKey: String,
    val commercialDetail: CommercialDetail,
    val created: Long,
    val createdAt: String,
    val deviceToken: String,
    val deviceType: Int,
    val editDetail: Int,
    val email: String,
    val facebookId: Any,
    val googleId: Any,
    val id: Int,
    val isOnline: Int,
    val mobile: String,
    val notification: String,
    val remember_token: String,
    val role: Int,
    val socialType: Int,
    val status: Int,
    val stripeCustomerId: String,
    val twitterId: Any,
    val updated: Long,
    val updatedAt: String,
    val verified: Int,
    val walletAmount: String
)

data class CommercialDetail(
    val firstName: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val userId: Int
)