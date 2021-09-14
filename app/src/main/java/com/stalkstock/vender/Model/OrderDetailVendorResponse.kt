package com.stalkstock.vender.Model

data class OrderDetailVendorResponse(
    val body: Body,
    val code: Int, // 200
    val message: String, // Order detail fetch successfully.
    val success: Boolean // true
) {
    data class Body(
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
        val orderAddress: OrderAddress,
        val orderItems: List<OrderItem>,
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
    ) {
        data class OrderAddress(
            val address_line2: String, // address_limne
            val bidId: Int, // 0
            val city: String, // city
            val country: String, // country
            val geoLocation: String, // null
            val id: Int, // 5
            val latitude: String, // 39.00000000
            val longitude: String, // 75.00000000
            val orderId: Int, // 8
            val special_instruction: String,
            val state: String, // state
            val street_address: String, // street address
            val type: String, // 1
            val userId: Int, // 77
            val zipcode: String // 6767821
        )

        data class OrderItem(
            val id: Int, // 7
            val netAmount: String, // 10.00
            val product: Product,
            val productId: Int, // 28
            val qty: Int, // 1
            val total: String // 10.00
        ) {
            data class Product(
                val availability: Int, // 1
                val brandName: String, // gggg
                val id: Int, // 28
                val mrp: String, // 10.00
                val name: String, // pname
                val percentageDiscount: Int, // 0
                val productType: Int // 0
            )
        }
    }
}