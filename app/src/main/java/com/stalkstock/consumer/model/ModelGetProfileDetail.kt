package com.stalkstock.consumer.model

data class ModelGetProfileDetail(
    val body: Body,
    val code: Int, // 200
    val message: String, // User profile fetched successfully.
    val success: Boolean // true
) {
    data class Body(
        val deviceToken: String, // 666666
        val deviceType: Int, // 1
        val email: String, // anikaesash@mailinator.com
        val id: Int, // 66
        val mobile: String, // 2615889823681
        val notification: String, // on
        val role: Int, // 1
        val userDetail: UserDetail
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