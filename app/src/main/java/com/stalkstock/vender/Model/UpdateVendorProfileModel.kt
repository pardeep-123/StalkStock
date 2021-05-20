package com.stalkstock.vender.Model

data class UpdateVendorProfileModel(
    val body: Body,
    val code: Int, // 200
    val message: String, // User profile updated successfully.
    val success: Boolean // true
) {
    data class Body(
        val created: Int, // 1621410182
        val createdAt: String, // 2021-05-19T07:43:02.000Z
        val deviceToken: String, // 666666
        val deviceType: Int, // 1
        val email: String, // business@yopmail.com
        val id: Int, // 78
        val mobile: String, // 9495686683
        val notification: String, // on
        val remember_token: String,
        val role: Int, // 3
        val status: Int, // 1
        val updated: Int, // 1621412484
        val updatedAt: String, // 2021-05-19T08:21:24.000Z
        val vendorDetail: VendorDetail,
        val verified: Int // 1
    ) {
        data class VendorDetail(
            val firstName: String, // Sanju
            val id: Int, // 14
            val image: String, // http://3.13.214.27:8800/uploads/user/8c3078a7-d673-40da-9eee-3b62e7629279.jpg
            val lastName: String, // Baghla
            val shopAddress: String, // ahhfjffndidk
            val userId: Int // 78
        )
    }
}