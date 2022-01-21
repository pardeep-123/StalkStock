package com.stalkstock.common.model

data class ModelBusinessType(
    val body: List<Body>,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val id: Int,
        val name: String,
        val status: Int
    )
}