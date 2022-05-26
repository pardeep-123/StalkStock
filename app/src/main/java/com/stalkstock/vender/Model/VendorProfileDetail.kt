package com.stalkstock.vender.Model

data class VendorProfileDetail(
    val body: Body,
    val code: Int, // 200
    val message: String, // User profile fetched successfully.
    val success: Boolean // true
) {
    data class Body(
        val deviceToken: String, // 666666
        val deviceType: Int, // 1
        val email: String, // business@yopmail.com
        val id: Int, // 78
        val mobile: String, // 9495686683
        val notification: String, // off
        val role: Int, // 3
        val vendorDetail: VendorDetail
    ) {
        data class VendorDetail(
            val firstName: String, // Sanju
            val id: Int, // 14
            val image: String, // http://3.13.214.27:8800/uploads/user/8c3078a7-d673-40da-9eee-3b62e7629279.jpg
            val coverImage: String, // http://3.13.214.27:8800/uploads/user/8c3078a7-d673-40da-9eee-3b62e7629279.jpg
            val lastName: String, // Baghla
            val shopAddress: String, // ahhfjffndidk
            val userId: Int // 78
        )
    }
}