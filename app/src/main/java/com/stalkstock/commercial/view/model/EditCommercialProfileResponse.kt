package com.stalkstock.commercial.view.model

data class EditCommercialProfileResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
){
    data class Body(
        val authKey: String,
        val commercialDetail: CommercialDetail,
        val created: Int,
        val createdAt: String,
        val deviceToken: String,
        val deviceType: Int,
        val editDetail: Int,
        val email: String,
        val facebookId: String,
        val googleId: Int,
        val id: Int,
        val isOnline: Int,
        val mobile: String,
        val notification: String,
        val remember_token: String,
        val role: Int,
        val socialType: Int,
        val status: Int,
        val stripeCustomerId: String,
        val twitterId: Int,
        val updated: Int,
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
}