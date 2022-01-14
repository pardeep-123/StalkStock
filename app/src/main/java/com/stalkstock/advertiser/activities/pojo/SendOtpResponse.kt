package com.stalkstock.advertiser.activities.pojo

data class SendOtpResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val otp: Int
    )
}