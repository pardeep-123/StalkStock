package com.stalkstock.driver.models

data class DriverProfileDetailResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // User profile fetched successfully.
    val success: Boolean // true
) {
    data class Body(
        val deviceToken: String, // 666666
        val deviceType: Int, // 1
        val driverDetail: DriverDetail,
        val email: String, // testdriver1@yopmail.com
        val id: Int, // 88
        val mobile: String, // 9988998822
        val notification: String, // off
        val role: Int // 2
    ) {
        data class DriverDetail(
            val firstName: String, // test1
            val id: Int, // 24
            val image: String, // http://3.13.214.27:8800/uploads/user/48572343-1b33-4ad4-af1a-906e42947b53.jpg
            val lastName: String, // driver
            val userId: Int // 88
        )
    }
}