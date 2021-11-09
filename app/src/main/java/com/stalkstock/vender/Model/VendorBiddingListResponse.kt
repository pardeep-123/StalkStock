package com.stalkstock.vender.Model

data class VendorBiddingListResponse(
    val body: ArrayList<BidData>,
    val code: Int, // 200
    val message: String, // Orders list.
    val success: Boolean // true
) {
    data class BidData(
        val acceptedLat: String, // 0.00000000
        val acceptedLong: String, // 0.00000000
        val createdAt: String, // 2021-07-12T13:09:28.000Z
        val customerId: Int, // 84
        val deliveryDate: String, // 2021-07-12
        val deliveryTime: String, // 13:09:28
        val driverId: Int, // 0
        val firstName: String, // him
        val id: Int, // 8
        val isDriverReview: Int, // 0
        val isSelfpickup: Int, // 1
        val isUserReview: Int, // 0
        val lastName: String, // g
        val netAmount: String, // 10.00
        val orderNo: String, // O16260953682501
        val orderStatus: Int, // 0
        val paymentMethod: Int, // 1
        val role: Int, // 1
        val shippingCharges: String, // 2.00
        val shopCharges: String, // 0.00
        val total: String, // 12.00
        val updatedAt: String, // 2021-07-12T13:09:28.000Z
        val vendorCommission: String, // 0.00
        val vendorId: Int // 85
    )
}