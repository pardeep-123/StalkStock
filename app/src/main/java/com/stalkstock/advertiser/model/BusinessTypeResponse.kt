package com.stalkstock.advertiser.model

data class BusinessTypeResponse(
    val body: List<Body>,
    val code: Int,
    val message: String,
    val success: Boolean
){
    data class Body(
        val id: Int,
        val name: String,
        val status: Int
    )
}
