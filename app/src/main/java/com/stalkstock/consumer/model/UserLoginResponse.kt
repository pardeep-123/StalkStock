package com.stalkstock.consumer.model

data class UserLoginResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // User logged in successfully.
    val success: Boolean // true
) {
    data class Body(
        val created: Int, // 1620628728
        val createdAt: String, // 2021-05-10T06:38:48.000Z
        val deviceToken: String, // 666666
        val deviceType: String, // 1
        val email: String, // anikaesash@mailinator.com
        val id: Int, // 66
        val mobile: String, // 2615889823681
        val notification: String, // on
        val remember_token: String,
        val role: Int, // 1
        val status: Int, // 1
        val token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjo2NiwiZW1haWwiOiJhbmlrYWVzYXNoQG1haWxpbmF0b3IuY29tIn0sImlhdCI6MTYyMDkwOTA5Mn0.5AJjo_bFPKDMdsojl459XdpN0JzU4ldObT85N91beno
        val updated: Int, // 1620909093
        val updatedAt: String, // 2021-05-13T12:31:32.000Z
        val userDetail: UserDetail,
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

        data class UserDetail(
            val created: Int, // 1620628728
            val createdAt: String, // 2021-05-10T06:38:48.000Z
            val first_name: String, // aniasd
            val id: Int, // 35
            val image: String, // http://3.13.214.27:8800/uploads/default/avatar-1.png
            val last_name: String, // kesh
            val updated: Int, // 1620628728
            val updatedAt: String, // 2021-05-10T06:38:48.000Z
            val userId: Int // 66
        )
    }
}