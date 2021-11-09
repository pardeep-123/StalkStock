package com.stalkstock.commercial.view.model

data class CommericalOrderPlaceResponse(
    val body: BodyXX,
    val code: Int,
    val message: String,
    val success: Boolean
){
    data class BodyXX(
        val acceptedLat: String,
        val acceptedLong: String,
        val adminCommission: String,
        val cardId: Int,
        val created: Int,
        val createdAt: String,
        val customerId: Int,
        val deliveryDate: Any,
        val deliveryTime: String,
        val driverId: Int,
        val id: Int,
        val isDriverReview: Int,
        val isSelfpickup: Int,
        val isUserReview: Int,
        val netAmount: String,
        val orderNo: String,
        val orderStatus: Int,
        val paymentMethod: Int,
        val qty: Int,
        val role: Int,
        val shippingCharges: String,
        val shopCharges: String,
        val taxCharged: String,
        val total: String,
        val transactionDetail: String,
        val transactionId: String,
        val updated: Int,
        val updatedAt: String,
        val vendorCommission: String,
        val vendorId: Int
    )
}

