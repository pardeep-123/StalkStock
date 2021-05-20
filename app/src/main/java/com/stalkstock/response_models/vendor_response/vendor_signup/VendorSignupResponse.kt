package com.stalkstock.response_models.vendor_response.vendor_signup

data class VendorSignupResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // Vendor signed up successfully.
    val success: Boolean // true
) {
    data class Body(
        val created: Int, // 1621403556
        val createdAt: String, // 2021-05-19T05:52:35.000Z
        val deviceToken: String,
        val deviceType: Int, // 0
        val email: String, // shop1@yopmail.com
        val id: Int, // 74
        val mobile: String, // 26159823
        val notification: String, // off
        val remember_token: String,
        val role: Int, // 3
        val status: Int, // 1
        val token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjo3NCwiZW1haWwiOiJzaG9wMUB5b3BtYWlsLmNvbSJ9LCJpYXQiOjE2MjE0MDM1NTZ9.N4Gn45ZgluewMYYFzOS8JWG-J1EwSqDyIndm4OFB2V0
        val updated: Int, // 1621403556
        val updatedAt: String, // 2021-05-19T05:52:35.000Z
        val vendorDetail: VendorDetail,
        val verified: Int // 1
    ) {
        data class VendorDetail(
            val accountHolderName: String,
            val accountNumber: String,
            val addressLine2: String,
            val approvalStatus: Int, // 0
            val approvalStatusReason: String,
            val bankAddress: String,
            val bankBranch: String,
            val bankName: String,
            val bsbNumber: String,
            val buisnessLicense: String, // 123456789
            val buisnessPhone: String, // 123456788
            val buisnessTypeId: Int, // 1
            val city: String, // ludhiana
            val country: String, // India
            val created: Int, // 1621403556
            val createdAt: String, // 2021-05-19T05:52:35.000Z
            val deliveryPolicy: String,
            val deliveryTime: Int, // 0
            val firstName: String, // shoper
            val geoLocation: String,
            val id: Int, // 10
            val ifscSwiftCode: String,
            val image: String, // http://3.13.214.27:8800/uploads/default/avatar-1.png
            val lastName: String, // king
            val latitude: String, // 0.00000000
            val longitude: String, // 0.00000000
            val paymentPolicy: String,
            val postalCode: String, // 141016
            val sellerInformation: String,
            val shopAddress: String, // samrat colony
            val shopCharges: String, // 0.00
            val shopDescription: String, // descr
            val shopLogo: String,
            val shopName: String, // Shop King
            val state: String, // punjab
            val taxInPercent: Int, // 0
            val taxValue: Int, // 0
            val updated: Int, // 1621403556
            val updatedAt: String, // 2021-05-19T05:52:35.000Z
            val userId: Int, // 74
            val website: String // www.com
        )
    }
}