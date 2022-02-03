package com.stalkstock.consumer.model

data class UserBannerModel(
    val body: List<Body>,
    val code: Int, // 200
    val message: String, // Banner list.
    val success: Boolean // true
) {
    data class Body(
        val id: Int, // 2
        val image: String, // http://3.13.214.27:8800/uploads/banner/banner2.jpg
        val status: Int, // 1
        val title: String,
        val userId: String,
        val startDate: String,
        val endDate: String,
        val action: String,
        val adLink: String,
        val budget: String,
        val description: String
    )
}