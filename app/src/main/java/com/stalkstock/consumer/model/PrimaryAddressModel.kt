package com.stalkstock.consumer.model

data class PrimaryAddressModel(
    val body: ModelUserAddressList.Body,
    val code: Int,
    val message: String,
    val success: Boolean
)