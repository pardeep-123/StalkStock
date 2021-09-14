package com.stalkstock.consumer.model

data class AddRecentSearchResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // search added successfully.
    val success: Boolean // true
) {
    data class Body(
        val created: Int, // 1626346515
        val createdAt: String, // 2021-07-15T10:55:15.000Z
        val id: Int, // 4
        val productId: Int, // 28
        val updated: Int, // 1626346515
        val updatedAt: String, // 2021-07-15T10:55:15.000Z
        val userId: Int // 84
    )
}