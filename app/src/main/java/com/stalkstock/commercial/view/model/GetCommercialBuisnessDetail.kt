package com.stalkstock.commercial.view.model

data class GetCommercialBuisnessDetail(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
){
    data class Body(
        val commercialDetail: CommercialDetail,
        val deviceToken: String,
        val deviceType: Int,
        val email: String,
        val id: Int,
        val mobile: String,
        val notification: String,
        val role: Int
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
}