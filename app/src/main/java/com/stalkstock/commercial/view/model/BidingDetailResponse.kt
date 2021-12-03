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
        val deliveryCharges: String,
        val orderItems: List<OrderItem>,
        val requestNo: String,
        val userId: Int,
        val vendorBidingRequest: VendorBidingRequest,
        val vendorId: Int,
        val chatId: Int
    )
    data class OrderItem(
        val bidId: Int,
        val id: Int,
        val product: Product,
        val productId: Int,
        val qty: Int,
        val userId: Int,
        val measurementId: Int,
        val measurementName: String
    )
    data class Product(
        val categoryId: Int,
        val categoryName: String,
        val id: Int,
        val measurementId: Int,
        val measurementName: String,
        val name: String
    )

    data class VendorBidingRequest(
        val amount: String,
        val bidId: Int,
        val description: String,
        val requestStatus: Int,
        val vendorDetail: VendorDetail,
        val vendorId: Int
    )

    data class VendorDetail(
        val firstName: String,
        val id: Int,
        val image: String,
        val lastName: String,
        val shopLogo: String,
        val shopName: String,
        val shopCharges: String
    )
}