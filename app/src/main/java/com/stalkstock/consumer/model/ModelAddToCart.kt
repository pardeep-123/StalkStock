package com.stalkstock.consumer.model

data class ModelAddToCart(
    val body: Body,
    val code: Int, // 200
    val message: String, // Product add to cart successfully.
    val success: Boolean // true
) {
    data class Body(
//        val created: Int, // 1622722757
//        val createdAt: String, // 2021-06-03T12:19:17.000Z
        val id: String, // 5
        val productId: String, // 24
        val quantity: String, // 1
//        val updated: Long, // 1622722891
//        val updatedAt: String, // 2021-06-03T12:21:30.000Z
        val userId: String, // 66
        val vendorId: String // 78
    )
}
