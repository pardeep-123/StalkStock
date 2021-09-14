package com.stalkstock.consumer.model

import java.io.Serializable

data class ModelUserAddressList(
    val body: List<Body>,
    val code: Int, // 200
    val message: String, // User address list.
    val success: Boolean // true
):Serializable {
    data class Body(
        val address_line2: String, // address_limne
        val geoLocation: String, // address_limne
        val city: String, // city
        val country: String, // country
        val created: Int, // 1619092567
        val createdAt: String, // 2021-04-22T11:56:07.000Z
        val id: Int, // 5
        val isDefault: String, // 1
        val latitude: String, // 33.00000000
        val longitude: String, // 75.00000000
        val special_instruction: String,
        val state: String, // state
        val street_address: String, // street address
        val type: String, // 1
        val updated: Int, // 1619093373
        val updatedAt: String, // 2021-04-22T12:09:33.000Z
        val userId: Int, // 60
        val zipcode: String // 6767821
    ):Serializable
}