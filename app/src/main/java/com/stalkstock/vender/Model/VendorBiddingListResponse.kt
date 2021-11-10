package com.stalkstock.vender.Model

data class VendorBiddingListResponse(
    val body: List<BidData>,
    val code: Int,
    val message: String,
    val success: Boolean
)

data class BidData(
    val bidCount: Int,
    val commercialDetail: CommercialDetail,
    val createdAt: String,
    val id: Int,
    val instruction: String,
    val requestNo: String,
    val userId: Int,
    val orderItems: List<OrderItem>,
    val vendorBidingRequest: VendorBidingRequest,
    val vendorId: Int
)

data class CommercialDetail(
    val firstName: String,
    val id: Int,
    val image: String,
    val lastName: String
)

data class VendorBidingRequest(
    val amount: String,
    val bidId: Int,
    val requestStatus: Int
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