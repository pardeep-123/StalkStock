package com.stalkstock.common.model

data class ModelCategoryList(
    val body: List<Body>,
    val code: Int, // 200
    val message: String, // Category list.
    val success: Boolean // true
) {
    data class Body(
        val id: Int, // 1
        val image: String,
        val name: String, // Devices
        val status: Int // 1
    )
}