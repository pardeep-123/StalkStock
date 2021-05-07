package com.stalkstock.response_models.user_response.user_signup

data class UserSignupResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val created: Int,
        val createdAt: String,
        val deviceToken: String,
        val deviceType: Int,
        val email: String,
        val id: Int,
        val mobile: String,
        val notification: String,
        val role: Int,
        val status: Int,
        val token: String,
        val updated: Int,
        val updatedAt: String,
        val verified: Int
    )
}