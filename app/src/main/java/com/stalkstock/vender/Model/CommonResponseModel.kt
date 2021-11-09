package com.stalkstock.vender.Model

data class CommonResponseModel(
    val body: ModelAddProduct.Body,
    val code: Int, // 200
    val message: String, // Product added successfully.
    val success: Boolean // true
)