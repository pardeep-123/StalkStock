package com.stalkstock.consumer.model

data class ModelCartData(
    val body: Body,
    val code: Int, // 200
    val message: String, // Cart detail fetch successfully.
    val success: Boolean // true
) {
    data class Body(
        val address: Address,
        val cartData: List<CartData>,
        val deliveryCharges: DeliveryCharges,
        val shopDetail: ShopDetail,
        val card:Card
    ) {
        data class Address(
            val address_line2: String,
            val city: String, // Bathinda
            val country: String, // India
            val created: Int, // 1621234874
            val createdAt: String, // 2021-05-17T07:01:13.000Z
            val geoLocation: String, // Qila Rd, Telian Wala Mohalla, Old City, Bathinda, Punjab 151001, India
            val id: Int, // 44
            val isDefault: String, // 1
            val latitude: String, // 30.20666030
            val longitude: String, // 74.93859610
            val special_instruction: String,
            val state: String, // Punjab
            val street_address: String, // Qila Road
            val type: String, // 1
            val updated: Int, // 1621234874
            val updatedAt: String, // 2021-05-17T07:01:13.000Z
            val userId: Int, // 66
            val zipcode: String // 151001
        )

        data class CartData(
            val created: Int, // 1622727198
            val createdAt: String, // 2021-06-03T13:33:18.000Z
            val id: Int, // 14
            val product: Product,
            val productId: Int, // 24
            val quantity: Int, // 1
            val updated: Int, // 1622727198
            val updatedAt: String, // 2021-06-03T13:33:18.000Z
            val userId: Int, // 66
            val vendorId: Int // 78
        ) {
            data class Product(
                val availability: Int, // 1
                val brandName: String, // brand
                val id: Int, // 24
                val mrp: String, // 56.00
                val name: String, // dnnfn
                val oldMrp: String, // 5665656.00
                val percentageDiscount: Int, // 0
                val productType: Int // 0
            )
        }

        data class Card(
            val id: Int,
            val userId: Int,
            val stripeCardId: String,
            val isDefault: Int,
            val cardType: Int
        )

        data class DeliveryCharges(
            val comment: String, // USD
            val id: Int, // 2
            val name: String, // global_delivery_charge_for_seller
            val value: String // 2
        )

        data class ShopDetail(
            val ShopAddress: String, // fndidk
            val deliveryTime: Int, // 0
            val deliveryType: Int, // 0
            val geoLocation: String,
            val id: Int, // 14
            val latitude: String, // 0.00000000
            val longitude: String, // 0.00000000
            val shopCharges: String, // 0.00
            val shopLogo: String,
            val shopName: String, // business name
            val userId: Int // 78
        )
    }
}


