package com.stalkstock.consumer.model

data class RecentSearchListResponse(
    val body: List<Body>,
    val code: Int, // 200
    val message: String, // search list.
    val success: Boolean // true
) {
    data class Body(
        val id: Int, // 4
        val productId: Int, // 28
        val search: String, // pname
        val userId: Int // 84
    )
}