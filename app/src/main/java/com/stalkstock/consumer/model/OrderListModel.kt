package com.stalkstock.consumer.model

data class OrderListModel(
    val body: List<Body>,
    val code: Int, // 200
    val message: String, // Order list.
    val success: Boolean // true
) {
    data class Body(
        val acceptedLat: String, // 0.00000000
        val acceptedLong: String, // 0.00000000
        val createdAt: String, // 2021-07-09T11:14:42.000Z
        val customerId: Int, // 84
        val deliveryDate: String, // 2021-07-09
        val deliveryTime: String, // 11:14:42
        val driverId: Int, // 0
        val id: Int, // 7
        val isDriverReview: Int, // 0
        val isSelfpickup: Int, // 1
        val isUserReview: Int, // 0
        val orderItems: List<OrderItem>,
        val orderNo: String, // O16258292825762
        val orderStatus: Int, // 0
        val orderVendor: OrderVendor,
        val paymentMethod: Int, // 1
        val role: Int, // 1
        val shopCharges: String, // 0.00
        val total: String, // 114.00
        val updatedAt: String, // 2021-07-09T11:14:42.000Z
        val vendorCommission: String, // 0.00
        val vendorId: Int // 78
    ) {
        data class OrderItem(
            val id: Int, // 6
            val product: Product,
            val productId: Int // 24
        ) {
            data class Product(
                val id: Int, // 24
                val name: String // dnnfn
            )
        }

        data class OrderVendor(
            val ShopAddress: String, // fndidk
            val geoLocation: String,
            val id: Int, // 14
            val latitude: String, // 0.00000000
            val longitude: String, // 0.00000000
            val shopLogo: String,
            val shopName: String, // business name
            val userId: Int // 78
        )
    }
}