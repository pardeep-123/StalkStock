package com.stalkstock.consumer.model

import java.io.Serializable

data class PlaceOrderModel(
    val isSelfpickup: String, // 1
    val netAmount: String, // 112
    val orderItem: ArrayList<OrderItem>,
    var paymentMethod: String, // 1     0=>Wallet 1=>Card 2=>cash
    val shippingCharges: String, // 2
    val shopCharges: String, // 0.00
    val total: String, // 114
    val totalQuantity: String, // 2
    val vendorId: String, // 78
    val addressId: String, // 78
    val transactionId:Int?=null
):Serializable {
    data class OrderItem(
        val netAmount: String, // 56.00
        val productId: Int, // 24
        val qty: Int, // 2
        val total: String // 112
    ):Serializable
}