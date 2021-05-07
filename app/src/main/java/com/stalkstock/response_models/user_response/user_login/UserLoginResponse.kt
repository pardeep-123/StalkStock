package com.stalkstock.response_models.user_response.user_login

data class UserLoginResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val created: Int,
        val createdAt: String,
        val deviceToken: String,
        val deviceType: String,
        val email: String,
        val id: Int,
        val mobile: String,
        val notification: String,
        val remember_token: String,
        val role: Int,
        val status: Int,
        val token: String,
        val updated: Int,
        val updatedAt: String,
        val verified: Int
    )
}