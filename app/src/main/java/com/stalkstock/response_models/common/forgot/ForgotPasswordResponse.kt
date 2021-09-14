package com.stalkstock.response_models.common.forgot

data class ForgotPasswordResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    class Body(
    )
}