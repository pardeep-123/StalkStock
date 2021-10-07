package com.stalkstock.advertiser.model

data class BuisnessDetailResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
)
{
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
        val name: String,
        val postalCode: String,
        val state: String,
        val updated: Int,
        val updatedAt: String,
        val userId: Int,
        val website: String
    )
}