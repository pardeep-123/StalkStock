package com.stalkstock.commercial.view.model

data class EditCommercialBuisnessDetail(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)

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
    val googleId: String,
    val id: Int,
    val isOnline: Int,
    val mobile: String,
    val notification: String,
    val remember_token: String,
    val role: Int,
    val socialType: Int,
    val status: Int,
    val stripeCustomerId: String,
    val twitterId: String,
    val updated: Int,
    val updatedAt: String,
    val verified: Int,
    val walletAmount: String
)

data class CommercialDetail(
    val addressLine2: String,
    val buisnessAddress: String,
    val buisnessDescription: String,
    val buisnessLicense: String,
    val buisnessLogo: String,
    val buisnessName: String,
    val buisnessPhone: String,
    val buisnessTypeId: Int,
    val buisnessTypeName: String,
    val city: String,
    val country: String,
    val created: Int,
    val createdAt: String,
    val firstName: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val postalCode: String,
    val state: String,
    val updated: Int,
    val updatedAt: String,
    val userId: Int,
    val website: String
)