package com.stalkstock.commercial.view.model

data class BidingDetailResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
){
    data class Body(
        val bidCount: Int,
        val createdAt: String,
        val id: Int,
        val instruction: String,
        val orderItems: List<OrderItem>,
        val requestNo: String,
        val userId: Int,
        val vendorBidingRequest: Any,
        val vendorId: Int
    )
    data class OrderItem(
        val bidId: Int,
        val id: Int,
        val product: Product,
        val productId: Int,
        val qty: Int,
        val userId: Int
    )
    data class Product(
        val categoryId: Int,
        val categoryName: String,
        val id: Int,
        val measurementId: Int,
        val measurementName: String,
        val name: String
    )
}