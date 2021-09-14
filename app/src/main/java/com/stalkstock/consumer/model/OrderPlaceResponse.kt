package com.stalkstock.consumer.model

data class OrderPlaceResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // Order Place successfully.
    val success: Boolean // true
) {
    data class Body(
        val acceptedLat: String, // 0.00000000
        val acceptedLong: String, // 0.00000000
        val adminCommission: String, // 0.00
        val created: Int, // 1625829282
        val createdAt: String, // 2021-07-09T11:14:42.000Z
        val customerId: Int, // 84
        val deliveryDate: String, // 0000-00-00
        val deliveryTime: String, // 00:00:00
        val driverId: Int, // 0
        val id: Int, // 7
        val isDriverReview: Int, // 0
        val isSelfpickup: Int, // 1
        val isUserReview: Int, // 0
        val netAmount: String, // 112.00
        val orderNo: String, // O16258292825762
        val orderStatus: Int, // 0
        val paymentMethod: Int, // 1
        val qty: Int, // 2
        val role: Int, // 1
        val shippingCharges: String, // 2.00
        val shopCharges: String, // 0.00
        val taxCharged: String, // 0.00
        val total: String, // 114.00
        val updated: Int, // 1625829282
        val updatedAt: String, // 2021-07-09T11:14:42.000Z
        val vendorCommission: String, // 0.00
        val vendorId: Int // 78
    )
}