package com.stalkstock.consumer.model

data class ModelUpdateProfile(
    val body: Body,
    val code: Int, // 200
    val message: String, // User profile updated successfully.
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
        val notification: String, // on
        val remember_token: String,
        val role: Int, // 1
        val status: Int, // 1
        val updated: Int, // 1620977882
        val updatedAt: String, // 2021-05-14T07:38:02.000Z
        val userDetail: UserDetail,
        val verified: Int // 1
    ) {
        data class UserDetail(
            val first_name: String, // Abhishe
            val id: Int, // 35
            val image: String, // http://3.13.214.27:8800/uploads/user/9cd79e46-c5aa-4a8a-b26b-03ffc240584d.jpg
            val last_name: String, // kumar
            val userId: Int // 66
        )
    }
}