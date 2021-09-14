package com.stalkstock.driver.models

data class CheckEmailResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // success
    val success: Boolean // true
) {
    class Body(
    )
}