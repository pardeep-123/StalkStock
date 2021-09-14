package com.stalkstock.driver.models

data class EditDriverResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // User profile updated successfully.
    val success: Boolean // true
) {
    data class Body(
        val created: Int, // 1626692741
        val createdAt: String, // 2021-07-19T11:05:40.000Z
        val deviceToken: String, // 666666
        val deviceType: Int, // 1
        val driverDetail: DriverDetail,
        val email: String, // testdriver1@yopmail.com
        val id: Int, // 88
        val isOnline: Int, // 0
        val mobile: String, // 9988998822
        val notification: String, // off
        val remember_token: String,
        val role: Int, // 2
        val status: Int, // 1
        val stripeCustomerId: String,
        val updated: Int, // 1626864197
        val updatedAt: String, // 2021-07-21T10:43:17.000Z
        val verified: Int, // 1
        val walletAmount: String // 0.00
    ) {
        data class DriverDetail(
            val firstName: String, // testd1
            val id: Int, // 24
            val image: String, // http://3.13.214.27:8800/uploads/user/48572343-1b33-4ad4-af1a-906e42947b53.jpg
            val lastName: String, // driver
            val userId: Int // 88
        )
    }
}