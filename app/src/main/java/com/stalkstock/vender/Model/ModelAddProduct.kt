package com.stalkstock.vender.Model

data class ModelAddProduct(
    val body: Body,
    val code: Int, // 200
    val message: String, // Product added successfully.
    val success: Boolean // true
) {
    class Body(
    )
}