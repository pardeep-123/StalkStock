package com.stalkstock.consumer.model

data class UserCommonModel(
    val body: Body,
    val code: Int, // 200
    val message: String, // User Notification status changed successfully.
    val success: Boolean // true
) {
    data class Body(
        val created: Int, // 1620628728
        val createdAt: String, // 2021-05-10T06:38:48.000Z
        val deviceToken: String, // 666666
        val deviceType: Int, // 1
        val email: String, // anikaesash@mailinator.com
        val id: Int, // 66
        val mobile: String, // 2615889823681
        val notification: String,
        val remember_token: String,
        val role: Int, // 1
        val status: Int, // 1
        val updated: Int, // 1620650590
        val updatedAt: String, // 2021-05-10T12:43:10.000Z
        val verified: Int // 1
    )
}