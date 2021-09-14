package com.stalkstock.common.model

data class ModelMeasurementList(
    val body: List<Body>,
    val code: Int, // 200
    val message: String, // Measurement list.
    val success: Boolean // true
) {
    data class Body(
        val id: Int, // 1
        val name: String, // Tea Spon
        val status: Int // 1
    )
}