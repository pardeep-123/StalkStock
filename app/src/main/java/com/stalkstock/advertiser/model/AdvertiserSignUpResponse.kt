package com.stalkstock.advertiser.model

data class AdvertiserSignUpResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val advertiserDetail: AdvertiserDetail,
        val created: Int,
        val createdAt: String,
        val deviceToken: String,
        val deviceType: Int,
        val email: String,
        val id: Int,
        val isOnline: Int,
        val mobile: String,
        val notification: String,
        val remember_token: String,
        val role: Int,
        val status: Int,
        val token: String,
        val updated: Int,
        val updatedAt: String,
        val verified: Int,
        val stripeCustomerId: String,
        val editDetail: Int,
        val authKey: String,
        val socialType: Int,
        val facebookId: String,
        val googleId: Int,
        val twitterId: Int,
        val walletAmount: String

    )

    data class AdvertiserDetail(
        val id: Int,
        val name: String,
        val firstName: String,
        val lastName: String,
        val image: String,
        val buisnessLogo: String,
        val buisnessPhone: String,
        val buisnessName: String,
        val buisnessTypeId: Int,
        val buisnessLicense: String,
        val website: String,
        val city: String,
        val state: String,
        val country: String,
        val postalCode: String,
        val buisnessAddress: String,
        val addressLine2: String,
        val buisnessDescription: String,
        val created: Int,
        val updated: Int,
        val createdAt: String,
        val updatedAt: String,
        val userId: Int

    )
}
