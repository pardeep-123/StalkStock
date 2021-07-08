package com.stalkstock.vender.Model

import java.io.Serializable

data class VendorBusinessDetailResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // User buisness detail fetched successfully.
    val success: Boolean // true
):Serializable {
    data class Body(
        val deviceToken: String, // 666666
        val deviceType: Int, // 1
        val email: String, // testvendor@yopmail.com
        val id: Int, // 85
        val mobile: String, // 98125868088
        val notification: String, // off
        val role: Int, // 3
        val vendorDetail: VendorDetail
    ):Serializable {
        data class VendorDetail(
            val addressLine2: String,
            val buisnessLicense: String, // bhj
            val buisnessPhone: String, // 566855888
            val buisnessTypeId: Int, // 2
            val buisnessTypeName: String, // Wholesaler's
            val city: String, // vbj
            val country: String, // India
            val deliveryTime: Int, // 0
            val deliveryType: Int, // 0
            val firstName: String, // test
            val geoLocation: String,
            val id: Int, // 15
            val lastName: String, // vendor
            val latitude: String, // 0.00000000
            val longitude: String, // 0.00000000
            val postalCode: String, // 888
            val shopAddress: String, // vbbn
            val shopCharges: String, // 0.00
            val shopDescription: String, // jznznz
            val shopLogo: String,
            val shopName: String, // testvendorsss
            val state: String, // bbb
            val userId: Int, // 85
            val website: String // xcvbnn
        ):Serializable
    }
}